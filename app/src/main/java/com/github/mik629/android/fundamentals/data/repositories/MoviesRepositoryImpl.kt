package com.github.mik629.android.fundamentals.data.repositories

import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.db.models.*
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.github.mik629.android.fundamentals.data.network.model.ActorDTO
import com.github.mik629.android.fundamentals.data.network.model.toGenre
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val serverApi: ServerApi,
    private val movieDao: MovieDao
) : MoviesRepository {

    override suspend fun getMovies(): List<Movie> {
        return withContext(Dispatchers.IO) {
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

                    with(movieDetails) {
                        res.add(
                            Movie(
                                id = id.toString(),
                                title = title,
                                overview = overview,
                                poster = posterPath,
                                backdrop = backdropPath, actors,
                                genres = genres.map(::toGenre),
                                minAge = if (isAdult) 18 else 0,
                                reviews = reviews,
                                rating = rating,
                                runtime = runtime
                            )
                        )
                    }
                }

                saveToDb(movieDao, res)
                res.sortedByDescending { it.rating }
            } else {
                cachedMovies.map(::toMovie)
            }
        }
    }

    private fun saveToDb(movieDao: MovieDao, res: MutableList<Movie>) {
        GlobalScope.launch {
            movieDao.insertData(
                res.map {
                    with(it) {
                        MovieDbEntity(
                            movieId = id,
                            title = title,
                            overview = overview,
                            posterImageUrl = poster,
                            backdropImageUrl = backdrop,
                            minAge = minAge,
                            reviews = reviews,
                            rating = rating,
                            runtime = runtime
                        )
                    }
                },

                res.flatMap { it.actors }
                    .distinct()
                    .map { ActorDbEntity(it.id, it.name, it.photoUrl) },

                res.flatMap { movie ->
                    movie.actors.map { actor ->
                        MovieActorCrossRef(
                            movieId = movie.id,
                            actorId = actor.id
                        )
                    }
                },

                res.flatMap { it.genres }
                    .distinct()
                    .map { GenreDbEntity(it.id, it.name) },

                res.flatMap { movie ->
                    movie.genres.map { genre ->
                        MovieGenreCrossRef(
                            movieId = movie.id,
                            genreId = genre.id
                        )
                    }
                }
            )
        }
    }
}
