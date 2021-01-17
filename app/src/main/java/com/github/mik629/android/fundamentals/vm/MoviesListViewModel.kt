package com.github.mik629.android.fundamentals.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mik629.android.fundamentals.domain.model.MovieItem
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MoviesListViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)
    private val _movies: MutableLiveData<List<MovieItem>> = MutableLiveData(emptyList())
    val movies: LiveData<List<MovieItem>> = _movies

    init {
        scope.launch {
            runCatching {
                _movies.value = moviesRepository.getMovies()
            }.onFailure {
                Timber.e(it)
            }
        }
    }
}
