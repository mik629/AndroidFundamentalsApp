package com.github.mik629.android.fundamentals.data.repositories

import com.github.mik629.android.fundamentals.data.MoviesLoader
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesLoader: MoviesLoader
) : MoviesRepository {

    override suspend fun getMovies() =
        withContext(Dispatchers.IO) {
            val cachedMovies = moviesLoader.getMoviesFromCache()
            if (cachedMovies.isEmpty()) {
                moviesLoader.loadMoviesFromNetwork()
            } else {
                cachedMovies
            }
        }

    override suspend fun getMovie(id: Long): Movie =
        withContext(Dispatchers.IO) {
            moviesLoader.getMovieFromCache(id) ?: moviesLoader.loadMovieFromNetwork(id = id)
        }
}
