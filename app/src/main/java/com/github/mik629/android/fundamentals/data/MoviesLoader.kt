package com.github.mik629.android.fundamentals.data

import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.db.models.MovieWithActorsAndGenres
import com.github.mik629.android.fundamentals.data.db.models.toMovie
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.github.mik629.android.fundamentals.data.network.model.ActorDTO
import com.github.mik629.android.fundamentals.data.network.model.toActor
import com.github.mik629.android.fundamentals.data.network.model.toMovie
import com.github.mik629.android.fundamentals.domain.model.Movie
import timber.log.Timber
import javax.inject.Inject

class MoviesLoader @Inject constructor(
    private val serverApi: ServerApi,
    private val dao: MovieDao
) {
    suspend fun loadMoviesFromCache(): List<Movie> =
        dao.getAllMovies()
            .map(MovieWithActorsAndGenres::toMovie)

    suspend fun loadMoviesFromNetwork(category: String = "popular"): List<Movie> {
        Timber.d("Loading from the network")
        return serverApi.getMovieList(category)
            .results
            .map { movie ->
                val movieDetails = serverApi.getMovieDetails(movie.id)
                val actors = serverApi.getMovieActors(movieDetails.id)
                    .cast
                    .map(ActorDTO::toActor)

                movieDetails.toMovie(actors)
            }.sortedByDescending { movie -> movie.rating }
    }

    suspend fun getMovieDetails(id: Long): Movie =
        getMovieDetailsFromCache(id) ?: loadMovieDetailsFromNetwork(id)

    private suspend fun getMovieDetailsFromCache(id: Long) =
        dao.getMovie(id)
            ?.toMovie()

    private suspend fun loadMovieDetailsFromNetwork(id: Long): Movie {
        val actors = serverApi.getMovieActors(id)
            .cast
            .map(ActorDTO::toActor)
        return serverApi.getMovieDetails(id)
            .toMovie(actors)
    }
}