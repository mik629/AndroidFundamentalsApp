package com.github.mik629.android.fundamentals.data.repositories

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val serverApi: ServerApi,
    private val dao: MovieDao
) : MoviesRepository {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private lateinit var imageBaseUrl: String

    init {
        scope.launch {
            imageBaseUrl = "${serverApi.getConfiguration().baseUrlInfo.baseUrl}original"
        }
    }

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

    private suspend fun loadMoviesFromNetwork(category: String = "popular"): List<Movie> {
        Timber.d("Loading from the network")
        return serverApi.getMovieList(category)
            .results
            .map { movie -> scope.async { loadMovieFromNetwork(movie.id) } }
            .awaitAll()
            .sortedByDescending { movie -> movie.rating }
            .also { movies -> save(movies) }
    }

    private suspend fun getMovieFromCache(id: Long): Movie? =
        dao.getMovie(id)
            ?.toMovie()

    private suspend fun loadMovieFromNetwork(id: Long): Movie {
        val actors = scope.async {
            serverApi.getMovieActors(id)
                .cast
                .map { dto -> dto.toActor(imageBaseUrl = imageBaseUrl) }
        }
        val movie = scope.async { serverApi.getMovie(id) }
        return movie.await()
            .toMovie(actors = actors.await(), imageBaseUrl = imageBaseUrl)
    }

    private fun save(res: List<Movie>) {
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
