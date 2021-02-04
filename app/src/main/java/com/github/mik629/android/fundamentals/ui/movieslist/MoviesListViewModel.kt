package com.github.mik629.android.fundamentals.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mik629.android.fundamentals.domain.model.MovieItem
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)
    private val _movies: MutableLiveData<List<MovieItem>> = MutableLiveData()
    val movies: LiveData<List<MovieItem>> = _movies

    init {
        scope.launch {
            _movies.postValue(moviesRepository.getMovies())
        }
    }
}
