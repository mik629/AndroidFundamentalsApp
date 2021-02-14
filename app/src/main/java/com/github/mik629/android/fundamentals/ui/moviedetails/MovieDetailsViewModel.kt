package com.github.mik629.android.fundamentals.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @Named(FragmentMovieDetails.ARG_MOVIE_ID) id: Long
) : ViewModel() {

    private val _movieDetails: MutableLiveData<Movie> = MutableLiveData()
    val movieDetails: LiveData<Movie>
        get() =
            _movieDetails

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() =
            _error

    init {
        viewModelScope.launch {
            runCatching {
                moviesRepository.getMovie(id)
            }.onSuccess {
                _movieDetails.value = it
            }.onFailure {
                _error.value = it
            }
        }
    }
}
