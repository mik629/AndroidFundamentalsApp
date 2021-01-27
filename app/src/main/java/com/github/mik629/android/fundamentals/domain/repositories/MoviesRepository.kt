package com.github.mik629.android.fundamentals.domain.repositories

import com.github.mik629.android.fundamentals.domain.model.MovieItem

interface MoviesRepository {
    suspend fun getMovies(): List<MovieItem>
}