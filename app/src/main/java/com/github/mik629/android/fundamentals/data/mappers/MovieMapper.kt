package com.github.mik629.android.fundamentals.data.mappers

import com.github.mik629.android.fundamentals.data.db.models.ActorDbEntity
import com.github.mik629.android.fundamentals.data.db.models.GenreDbEntity
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity
import com.github.mik629.android.fundamentals.data.db.models.MovieWithActorsAndGenres
import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.domain.model.Genre
import com.github.mik629.android.fundamentals.domain.model.Movie

class MovieMapper : Mapper<MovieWithActorsAndGenres, Movie> {
    override fun map(obj: MovieWithActorsAndGenres) =
        with(obj.movieEntity) {
            Movie(
                movieId,
                title,
                overview,
                poster,
                backdrop,
                obj.actors.map { Actor(it.actorId, it.name, it.photoUrl) },
                obj.genres.map { Genre(it.genreId, it.name) },
                minAge,
                reviews,
                rating,
                runtime
            )
        }

    override fun reverseMap(obj: Movie) =
        with(obj) {
            MovieWithActorsAndGenres(
                MovieDbEntity(
                    id,
                    title,
                    overview,
                    poster,
                    backdrop,
                    minAge,
                    reviews,
                    rating,
                    runtime
                ),
                actors.map { ActorDbEntity(it.id, it.name, it.photoUrl) },
                genres.map { GenreDbEntity(it.id, it.name) }
            )
        }
}