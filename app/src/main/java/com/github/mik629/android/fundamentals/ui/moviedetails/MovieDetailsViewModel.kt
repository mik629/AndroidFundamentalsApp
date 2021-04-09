package com.github.mik629.android.fundamentals.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import com.github.mik629.android.fundamentals.ui.ViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieDetailsViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val id: Long
) : ViewModel() {

    private val _movieDetails: MutableLiveData<ViewState<Movie, Throwable>> = MutableLiveData()
    val movieDetails: LiveData<ViewState<Movie, Throwable>>
        get() =
            _movieDetails

    init {
        viewModelScope.launch {
            _movieDetails.value = ViewState.loading()
            runCatching {
                moviesRepository.getMovie(id)
            }.onSuccess { movie ->
                _movieDetails.value = ViewState.success(data = movie)
            }.onFailure { e ->
                _movieDetails.value = ViewState.error(error = e)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(movieId: Long): MovieDetailsViewModel
    }
}

class MovieDetailsViewModelFactory @AssistedInject constructor(
    @Assisted private val id: Long,
    private val viewModelFactory: MovieDetailsViewModel.Factory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == MovieDetailsViewModel::class.java)
        return viewModelFactory.create(movieId = id) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long): MovieDetailsViewModelFactory
    }
}
