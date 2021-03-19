package com.github.mik629.android.fundamentals.di.modules

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.github.mik629.android.fundamentals.data.MoviesLoader
import com.github.mik629.android.fundamentals.data.db.MovieDb
import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.github.mik629.android.fundamentals.data.repositories.MoviesRepositoryImpl
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import com.github.mik629.android.fundamentals.ui.movieslist.MoviesListViewModelFactory
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
    @Singleton
    fun provideMoviesListViewModelFactory(factory: MoviesListViewModelFactory): ViewModelProvider.Factory =
        factory

    @Provides
    @Singleton
    fun provideMovieDao(context: Context): MovieDao =
        MovieDb.createDb(context).dao

    @Provides
    @Singleton
    fun provideMoviesLoader(serverApi: ServerApi, movieDao: MovieDao) =
        MoviesLoader(serverApi, movieDao)
}