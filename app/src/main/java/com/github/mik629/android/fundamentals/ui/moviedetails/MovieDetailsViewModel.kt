package com.github.mik629.android.fundamentals.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mik629.android.fundamentals.di.AssistedSavedStateViewModelFactory
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieDetailsViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<MovieDetailsViewModel>

    private val _movieDetails: MutableLiveData<Movie> = MutableLiveData()
    val movieDetails: LiveData<Movie>
        get() =
            _movieDetails

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() =
            _error

    private val _id: MutableLiveData<Long> =
        savedStateHandle.getLiveData(FragmentMovieDetails.ARG_MOVIE_ID, -1)

    init {
        _id.observeForever { id ->
            if (id >= 0) {
                viewModelScope.launch {
                    runCatching {
                        moviesRepository.getMovie(id)
                    }.onSuccess {
                        _movieDetails.value = it
                    }.onFailure {
                        _error.value = it
                    }
                }
            }
        }
    }

    fun setId(id: Long) {
        _id.value = id
    }
}
