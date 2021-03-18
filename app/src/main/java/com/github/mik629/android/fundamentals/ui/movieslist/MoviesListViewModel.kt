package com.github.mik629.android.fundamentals.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mik629.android.fundamentals.di.AssistedSavedStateViewModelFactory
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import timber.log.Timber

class MoviesListViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<MoviesListViewModel>

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
