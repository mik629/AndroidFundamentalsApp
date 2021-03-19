package com.github.mik629.android.fundamentals.ui.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import javax.inject.Inject

class MoviesListViewModelFactory @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MoviesListViewModel(moviesRepository) as T
}
