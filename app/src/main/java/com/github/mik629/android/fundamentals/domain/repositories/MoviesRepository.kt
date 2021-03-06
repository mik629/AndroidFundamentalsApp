package com.github.mik629.android.fundamentals.domain.repositories

import com.github.mik629.android.fundamentals.domain.model.Movie

interface MoviesRepository {
    suspend fun getMovies(): List<Movie>

    suspend fun getMovie(id: Long): Movie

    suspend fun loadMoviesFromNetwork(category: String = "popular"): List<Movie>
}