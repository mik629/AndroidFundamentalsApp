package com.github.mik629.android.fundamentals.data

import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.db.models.MovieWithActorsAndGenres
import com.github.mik629.android.fundamentals.data.db.models.toCrossRef
import com.github.mik629.android.fundamentals.data.db.models.toEntity
import com.github.mik629.android.fundamentals.data.db.models.toMovie
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.github.mik629.android.fundamentals.data.network.model.ActorDTO
import com.github.mik629.android.fundamentals.data.network.model.toActor
import com.github.mik629.android.fundamentals.data.network.model.toMovie
import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.domain.model.Genre
import com.github.mik629.android.fundamentals.domain.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class MoviesLoader(
    private val serverApi: ServerApi,
    private val dao: MovieDao
) {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    suspend fun getMoviesFromCache(): List<Movie> =
        dao.getAllMovies()
            .map(MovieWithActorsAndGenres::toMovie)

    suspend fun loadMoviesFromNetwork(category: String = "popular"): List<Movie> {
        Timber.d("Loading from the network")
        return scope.async { serverApi.getMovieList(category) }
            .await()
            .results
            .map { movie -> scope.async { loadMovieFromNetwork(movie.id) }.await() }
            .sortedByDescending { movie -> movie.rating }
            .also { movies -> save(movies) }
    }

    suspend fun getMovieFromCache(id: Long): Movie? =
        dao.getMovie(id)
            ?.toMovie()

    suspend fun loadMovieFromNetwork(id: Long): Movie {
        val actors = scope.async {
            serverApi.getMovieActors(id)
                .cast
                .map(ActorDTO::toActor)
        }.await()

        return scope.async { serverApi.getMovie(id) }
            .await()
            .toMovie(actors)
    }

    private suspend fun save(res: List<Movie>) {
        scope.launch {
            Timber.d("Saving to db")
            dao.insertData(
                movies = res.map(Movie::toEntity),

                actors = res.flatMap { movie -> movie.actors }
                    .distinct()
                    .map(Actor::toEntity),

                movieActors = res.flatMap { movie ->
                    movie.actors.map { actor ->
                        actor.toCrossRef(movie)
                    }
                },

                genres = res.flatMap { movie -> movie.genres }
                    .distinct()
                    .map(Genre::toEntity),

                movieGenres = res.flatMap { movie ->
                    movie.genres.map { genre ->
                        genre.toCrossRef(movie)
                    }
                }
            )
        }
    }
}
