package com.github.mik629.android.fundamentals.vm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mik629.android.fundamentals.data.network.model.MovieItem
import com.github.mik629.android.fundamentals.global.repositories.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val moviesRepository: MoviesRepository,
    private val context: Context
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)
    private val _movies: MutableLiveData<List<MovieItem>> = MutableLiveData(emptyList())
    val movies: LiveData<List<MovieItem>> = _movies

    init {
        scope.launch {
            _movies.value = moviesRepository.getMovies(context)
        }
    }


}