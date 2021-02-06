package com.github.mik629.android.fundamentals.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>>
        get() =
            _movies

    init {
        viewModelScope.launch {
            _movies.value = moviesRepository.getMovies()
        }
    }
}
