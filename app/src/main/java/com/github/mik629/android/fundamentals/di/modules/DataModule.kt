package com.github.mik629.android.fundamentals.di.modules

import android.content.Context
import com.github.mik629.android.fundamentals.data.db.MovieDb
import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.repositories.MoviesRepositoryImpl
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
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
    fun provideMovieDb(context: Context): MovieDb =
        MovieDb.createDb(context)

    @Provides
    @Singleton
    fun provideMovieDao(movieDb: MovieDb): MovieDao =
        movieDb.dao
}