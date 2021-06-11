package com.github.mik629.android.fundamentals.data.repositories

import android.content.Context
import com.github.mik629.android.fundamentals.data.background.UpdateCacheWorker
import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.db.models.MovieWithActorsAndGenres
import com.github.mik629.android.fundamentals.data.db.models.toCrossRef
import com.github.mik629.android.fundamentals.data.db.models.toEntity
import com.github.mik629.android.fundamentals.data.db.models.toMovie
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.github.mik629.android.fundamentals.data.network.model.toActor
import com.github.mik629.android.fundamentals.data.network.model.toMovie
import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.domain.model.Genre
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
    private val dao: MovieDao,
    private val context: Context
) : MoviesRepository {
    private val imageBaseUrl: AtomicReference<String> = AtomicReference(null)
    private val isUpdateCacheWorkerRunning: AtomicBoolean = AtomicBoolean(false)

    override suspend fun getMovies(): List<Movie> {
        val cachedMovies = getMoviesFromCache()
        return if (cachedMovies.isEmpty()) {
            loadMoviesFromNetwork()
        } else {
            cachedMovies
        }
    }

    override suspend fun getMovie(id: Long): Movie {
        return getMovieFromCache(id) ?: loadMovieFromNetwork(id = id)
    }

    private suspend fun getMoviesFromCache(): List<Movie> =
        dao.getAllMovies()
            .map(MovieWithActorsAndGenres::toMovie)

    override suspend fun loadMoviesFromNetwork(category: String): List<Movie> =
        coroutineScope {
            Timber.d("Loading from the network")
            serverApi.getMovieList(category)
                .results
                .map { movie -> coroutineScope { async { loadMovieFromNetwork(movie.id) } } }
                .awaitAll()
                .sortedByDescending { movie -> movie.rating }
                .also { movies -> save(movies) }
        }.also {
            if (!isUpdateCacheWorkerRunning.get()) {
                UpdateCacheWorker.enqueueRequest(context = context)
                isUpdateCacheWorkerRunning.set(true)
            }
        }

    private suspend fun getMovieFromCache(id: Long): Movie? =
        dao.getMovie(id)
            ?.toMovie()

    private suspend fun loadMovieFromNetwork(id: Long): Movie =
        coroutineScope {
            val baseUrl = imageBaseUrl.get()
                ?: (serverApi.getConfiguration().baseUrlInfo.baseUrl + "original")
                    .also { baseUrl -> imageBaseUrl.set(baseUrl) }
            val actors =
                async {
                    serverApi.getMovieActors(id)
                        .cast
                        .map { dto -> dto.toActor(imageBaseUrl = baseUrl) }
                }
            val movie = coroutineScope { async { serverApi.getMovie(id) } }
            movie.await()
                .toMovie(actors = actors.await(), imageBaseUrl = baseUrl)
        }

    private suspend fun save(res: List<Movie>) {
        Timber.d("Saving to db")
        dao.insertData(
            movies = res.map(Movie::toEntity),

            actors = res.flatMap { movie -> movie.details.actors }
                .distinct()
                .map(Actor::toEntity),

            movieActors = res.flatMap { movie ->
                movie.details.actors.map { actor ->
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
