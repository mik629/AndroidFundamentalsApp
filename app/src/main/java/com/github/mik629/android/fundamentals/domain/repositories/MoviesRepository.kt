package com.github.mik629.android.fundamentals.domain.repositories

import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.model.MovieDetails

interface MoviesRepository {
    suspend fun getMovies(): List<Movie>

    suspend fun getMovieDetails(id: Long): MovieDetails
}