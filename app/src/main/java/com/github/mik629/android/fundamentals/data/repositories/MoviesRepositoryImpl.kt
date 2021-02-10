package com.github.mik629.android.fundamentals.data.repositories

import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
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
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val serverApi: ServerApi,
    private val movieDao: MovieDao
) : MoviesRepository {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override suspend fun getMovies() =
        withContext(Dispatchers.IO) {
            val cachedMovies = movieDao.getAllMovies()
            if (cachedMovies.isEmpty()) {
                val movies = serverApi.getMovieList()
                    .results

                val res = mutableListOf<Movie>()
                for (movie in movies) {
                    val movieDetails = serverApi.getMovieDetails(movie.id)
                    val actors = serverApi.getMovieActors(movieDetails.id)
                        .cast
                        .map(ActorDTO::toActor)

                    res.add(
                        movieDetails.toMovie(actors)
                    )
                }

                saveToDb(movieDao, res)
                res.sortedByDescending { it.rating }
            } else {
                cachedMovies.map { it.toMovie() }
            }
        }

    private fun saveToDb(movieDao: MovieDao, res: List<Movie>) {
        scope.launch {
            movieDao.insertData(
                res.map(Movie::toEntity),

                res.flatMap { it.actors }
                    .distinct()
                    .map(Actor::toEntity),

                res.flatMap { movie ->
                    movie.actors.map { actor ->
                        actor.toCrossRef(movie)
                    }
                },

                res.flatMap { it.genres }
                    .distinct()
                    .map(Genre::toEntity),

                res.flatMap { movie ->
                    movie.genres.map { genre ->
                        genre.toCrossRef(movie)
                    }
                }
            )
        }
    }
}
