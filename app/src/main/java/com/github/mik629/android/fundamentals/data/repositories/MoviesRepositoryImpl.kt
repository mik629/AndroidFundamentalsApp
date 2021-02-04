package com.github.mik629.android.fundamentals.data.repositories

import com.github.mik629.android.fundamentals.data.db.MovieDb
import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.db.models.*
import com.github.mik629.android.fundamentals.data.mappers.Mapper
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.github.mik629.android.fundamentals.data.network.model.ActorDTO
import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.domain.model.Genre
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val serverApi: ServerApi,
    private val movieDb: MovieDb,
    private val movieMapper: Mapper<MovieWithActorsAndGenres, Movie>,
    private val actorMapper: Mapper<ActorDTO, Actor>
) : MoviesRepository {

    override suspend fun getMovies(): List<Movie> {
        val movieDao = movieDb.dao
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
                        .map { actor -> actorMapper.map(actor) }

                    with(movieDetails) {
                        res.add(
                            Movie(
                                id, title, overview, posterPath, backdropPath, actors,
                                genres.map { genre -> Genre(genre.id, genre.name) },
                                if (isAdult) 18 else 0, reviews, rating, runtime
                            )
                        )
                    }
                }

                saveToDb(movieDao, res)
                res
            } else {
                cachedMovies.map(movieMapper::map)
            }
        }
    }

    private fun saveToDb(movieDao: MovieDao, res: MutableList<Movie>) {
        movieDb.runInTransaction {
            GlobalScope.launch {
                movieDao.insertMovies(
                    res.map {
                        with(it) {
                            MovieDbEntity(
                                movieId = id,
                                title = title,
                                overview = overview,
                                poster = poster,
                                backdrop = backdrop,
                                minAge = minAge,
                                reviews = reviews,
                                rating = rating,
                                runtime = runtime
                            )
                        }
                    }
                )
                movieDao.insertActors(
                    res.flatMap { it.actors }
                        .distinct()
                        .map { ActorDbEntity(it.id, it.name, it.photoUrl) }
                )
                movieDao.insertMovieActors(
                    res.flatMap { movie ->
                        movie.actors.map { actor ->
                            MovieActorCrossRef(
                                movieId = movie.id,
                                actorId = actor.id
                            )
                        }
                    }
                )

                movieDao.insertGenres(
                    res.flatMap { it.genres }
                        .distinct()
                        .map { GenreDbEntity(it.id, it.name) }
                )
                movieDao.insertMovieGenres(
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
}
