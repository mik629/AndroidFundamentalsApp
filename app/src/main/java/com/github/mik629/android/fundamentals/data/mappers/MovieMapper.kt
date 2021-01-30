package com.github.mik629.android.fundamentals.data.mappers

import com.github.mik629.android.fundamentals.data.db.models.ActorEntity
import com.github.mik629.android.fundamentals.data.db.models.GenreEntity
import com.github.mik629.android.fundamentals.data.db.models.MovieEntity
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
                obj.actors.map { ActorItem(it.actorId, it.name, it.ava) },
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
                MovieEntity(
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
                actors.map { ActorEntity(it.id, it.name, it.ava) },
                genres.map { GenreEntity(it.id, it.name) }
            )
        }
}