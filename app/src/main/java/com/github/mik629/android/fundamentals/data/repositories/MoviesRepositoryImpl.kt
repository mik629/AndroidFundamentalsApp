package com.github.mik629.android.fundamentals.data.repositories

import com.github.mik629.android.fundamentals.data.mappers.Mapper
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.github.mik629.android.fundamentals.data.network.model.Actor
import com.github.mik629.android.fundamentals.domain.model.ActorItem
import com.github.mik629.android.fundamentals.domain.model.GenreItem
import com.github.mik629.android.fundamentals.domain.model.MovieItem
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val serverApi: ServerApi,
    private val actorMapper: Mapper<Actor, ActorItem>
) : MoviesRepository {

    override suspend fun getMovies() = withContext(Dispatchers.IO) {
        val movies = serverApi.getMovieList()
            .results

        val res = mutableListOf<MovieItem>()
        for (movie in movies) {
            val movieDetails = serverApi.getMovieDetails(movie.id)
            val actors = serverApi.getMovieActors(movieDetails.id)
                .cast
                .map { actor -> actorMapper.map(actor) }

            with(movieDetails) {
                res.add(
                    MovieItem(
                        id, title, overview, posterPath, backdropPath,
                        genres.map { genre -> GenreItem(genre.id, genre.name) },
                        actors, if (isAdult) 18 else 0, reviews, rating, runtime
                    )
                )
            }
        }
        res
    }
}
