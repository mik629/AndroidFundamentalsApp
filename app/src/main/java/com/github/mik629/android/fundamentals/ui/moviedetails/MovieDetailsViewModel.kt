package com.github.mik629.android.fundamentals.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mik629.android.fundamentals.domain.model.MovieDetails
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository,
    private val id: Long
) : ViewModel() {

    private val _movieDetails: MutableLiveData<MovieDetails> = MutableLiveData()
    val movieDetails: LiveData<MovieDetails>
        get() =
            _movieDetails

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() =
            _error

    init {
        viewModelScope.launch {
            runCatching {
                moviesRepository.getMovieDetails(id)
            }.onSuccess {
                _movieDetails.value = it
            }.onFailure {
                _error.value = it
            }
        }
    }
}
