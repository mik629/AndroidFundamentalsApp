package com.github.mik629.android.fundamentals.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>>
        get() =
            _movies

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() =
            _error

    init {
        viewModelScope.launch {
            runCatching {
                moviesRepository.getMovies()
            }.onSuccess {
                _movies.value = it
            }.onFailure {
                _error.value = it
                Timber.e(it)
            }
        }
    }
}
