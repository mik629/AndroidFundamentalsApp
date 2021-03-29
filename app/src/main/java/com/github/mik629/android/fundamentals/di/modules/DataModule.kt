package com.github.mik629.android.fundamentals.di.modules

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.github.mik629.android.fundamentals.data.db.MovieDb
import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.repositories.MoviesRepositoryImpl
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import com.github.mik629.android.fundamentals.ui.movieslist.MoviesListViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DataModule {
    @Provides
    @Singleton
    fun provideMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository =
        moviesRepository

    @Provides
    fun provideMoviesListViewModelFactory(factory: MoviesListViewModel.Factory): ViewModelProvider.Factory =
        factory

    @Provides
    @Singleton
    fun provideMovieDb(context: Context): MovieDb =
        MovieDb.createDb(context)

    @Provides
    @Singleton
    fun provideMovieDao(movieDb: MovieDb): MovieDao =
        movieDb.dao
}