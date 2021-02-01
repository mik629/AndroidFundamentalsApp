package com.github.mik629.android.fundamentals.data.mappers

import com.github.mik629.android.fundamentals.data.db.models.ActorDbEntity
import com.github.mik629.android.fundamentals.data.db.models.GenreDbEntity
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity
import com.github.mik629.android.fundamentals.data.db.models.MovieWithActorsAndGenres
import com.github.mik629.android.fundamentals.domain.model.ActorItem
import com.github.mik629.android.fundamentals.domain.model.GenreItem
import com.github.mik629.android.fundamentals.domain.model.MovieItem

class MovieMapper : Mapper<MovieWithActorsAndGenres, MovieItem> {
    override fun map(obj: MovieWithActorsAndGenres) =
        with(obj.movieEntity) {
            MovieItem(
                movieId,
                title,
                overview,
                poster,
                backdrop,
                obj.actors.map { ActorItem(it.actorId, it.name, it.photoUrl) },
                obj.genres.map { GenreItem(it.genreId, it.name) },
                minAge,
                reviews,
                rating,
                runtime
            )
        }

    override fun reverseMap(obj: MovieItem) =
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