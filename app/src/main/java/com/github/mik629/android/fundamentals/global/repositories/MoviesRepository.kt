package com.github.mik629.android.fundamentals.global.repositories

import android.content.Context
import com.github.mik629.android.fundamentals.data.network.model.MovieItem

interface MoviesRepository {
    suspend fun getMovies(context: Context): List<MovieItem>
}