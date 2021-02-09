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

    private val cached = HashMap<String, Movie>()
    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>>
        get() =
            _movies

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() =
            _error

    fun getMovie(id: String) =
        requireNotNull(cached[id])

    init {
        viewModelScope.launch {
            runCatching {
                moviesRepository.getMovies()
            }.onSuccess {
                _movies.value = it
                it.forEach { item ->
                    cached[item.id] = item
                }
            }.onFailure {
                _error.value = it
            }
        }
    }
}
