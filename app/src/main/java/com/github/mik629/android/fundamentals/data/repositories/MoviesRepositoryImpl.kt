package com.github.mik629.android.fundamentals.data.repositories

import com.github.mik629.android.fundamentals.data.MoviesLoader
import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.db.models.toCrossRef
import com.github.mik629.android.fundamentals.data.db.models.toEntity
import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.domain.model.Genre
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.model.MovieDetails
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesLoader: MoviesLoader,
    private val movieDao: MovieDao
) : MoviesRepository {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override suspend fun getMovies() =
        withContext(Dispatchers.IO) {
            val cachedMovies = moviesLoader.loadMoviesFromCache()
            if (cachedMovies.isEmpty()) {
                val res = moviesLoader.loadMoviesFromNetwork()
                save(res)
                res.sortedByDescending { movie -> movie.rating }
            } else {
                cachedMovies
            }
        }

    override suspend fun getMovieDetails(id: Long): MovieDetails =
        withContext(Dispatchers.IO) {
            moviesLoader.getMovieDetails(id)
        }

    override suspend fun save(res: List<Movie>) {
        scope.launch {
            Timber.d("Saving to db")
            movieDao.insertData(
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
