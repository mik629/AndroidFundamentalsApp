package com.github.mik629.android.fundamentals.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieDetailsViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val id: Long
) : ViewModel() {

    private val _movieDetails: MutableLiveData<Movie> = MutableLiveData()
    val movieDetails: LiveData<Movie>
        get() =
            _movieDetails

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() =
            _error // convert to two states: success, error (like in hackathon)

    init {
        viewModelScope.launch {
            runCatching {
                moviesRepository.getMovie(id)
            }.onSuccess { movie ->
                _movieDetails.value = movie
            }.onFailure {
                _error.value = it
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted movieId: Long
        ): MovieDetailsViewModel
    }
}
