package com.github.mik629.android.fundamentals.data

import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.db.models.MovieWithActorsAndGenres
import com.github.mik629.android.fundamentals.data.db.models.toMovie
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.github.mik629.android.fundamentals.data.network.model.ActorDTO
import com.github.mik629.android.fundamentals.data.network.model.toActor
import com.github.mik629.android.fundamentals.data.network.model.toMovie
import com.github.mik629.android.fundamentals.domain.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import timber.log.Timber

class MoviesLoader constructor(
    private val serverApi: ServerApi,
    private val dao: MovieDao
) {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    suspend fun loadMoviesFromCache(): List<Movie> =
        dao.getAllMovies()
            .map(MovieWithActorsAndGenres::toMovie)

    suspend fun loadMoviesFromNetwork(category: String = "popular"): List<Movie> {
        Timber.d("Loading from the network")
        return scope.async { serverApi.getMovieList(category) }
            .await()
            .results
            .map { movie -> scope.async { loadMovieFromNetwork(movie.id) }.await() }
            .sortedByDescending { movie -> movie.rating }
    }

    suspend fun getMovie(id: Long): Movie =
        getMovieFromCache(id) ?: loadMovieFromNetwork(id)

    private suspend fun getMovieFromCache(id: Long) =
        dao.getMovie(id)
            ?.toMovie()

    private suspend fun loadMovieFromNetwork(id: Long): Movie {
        val actors = scope.async {
            serverApi.getMovieActors(id)
                .cast
                .map(ActorDTO::toActor)
        }.await()

        return scope.async { serverApi.getMovie(id) }
            .await()
            .toMovie(actors)
    }
}
