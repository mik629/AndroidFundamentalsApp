package com.github.mik629.android.fundamentals.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import com.github.mik629.android.fundamentals.ui.ViewState
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MoviesListViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _movies: MutableLiveData<ViewState<List<Movie>, Throwable>> = MutableLiveData()
    val movies: LiveData<ViewState<List<Movie>, Throwable>>
        get() =
            _movies

    init {
        viewModelScope.launch {
            _movies.value = ViewState.loading()
            runCatching {
                moviesRepository.getMovies()
            }.onSuccess { movies ->
                _movies.value = ViewState.success(data = movies)
            }.onFailure { e ->
                Timber.e(e)
                _movies.value = ViewState.error(error = e)
            }
        }
    }

    class Factory @Inject constructor(
        private val moviesRepository: MoviesRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == MoviesListViewModel::class.java)
            return MoviesListViewModel(moviesRepository) as T
        }

    }
}
