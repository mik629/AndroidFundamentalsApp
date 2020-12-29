package com.github.mik629.android.fundamentals.data.repositories

import android.content.Context
import com.github.mik629.android.fundamentals.data.loadMovies
import com.github.mik629.android.fundamentals.global.repositories.MoviesRepository

class MoviesRepositoryImpl : MoviesRepository {
    override suspend fun getMovies(context: Context) = loadMovies(context)
}