package com.github.mik629.android.fundamentals.di

import androidx.lifecycle.ViewModel
import com.github.mik629.android.fundamentals.ui.moviedetails.MovieDetailsViewModel
import com.github.mik629.android.fundamentals.ui.movieslist.MoviesListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MoviesListViewModel::class)
    abstract fun bindMoviesListViewModel(f: MoviesListViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(f: MovieDetailsViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
}