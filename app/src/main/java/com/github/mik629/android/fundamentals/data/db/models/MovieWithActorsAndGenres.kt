package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.github.mik629.android.fundamentals.data.db.models.ActorDbEntity.Companion.COLUMN_NAME_ACTOR_ID
import com.github.mik629.android.fundamentals.data.db.models.GenreDbEntity.Companion.COLUMN_NAME_GENRE_ID
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.COLUMN_NAME_MOVIE_ID
import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.domain.model.Genre
import com.github.mik629.android.fundamentals.domain.model.Movie

data class MovieWithActorsAndGenres(
    @Embedded
    val movieEntity: MovieDbEntity,
    @Relation(
        parentColumn = COLUMN_NAME_MOVIE_ID,
        entityColumn = COLUMN_NAME_ACTOR_ID,
        associateBy = Junction(MovieActorCrossRef::class)
    )
    val actors: List<ActorDbEntity>,
    @Relation(
        parentColumn = COLUMN_NAME_MOVIE_ID,
        entityColumn = COLUMN_NAME_GENRE_ID,
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreDbEntity>
)

fun toMovie(entity: MovieWithActorsAndGenres) =
    Movie(
        id = entity.movieEntity.movieId,
        title = entity.movieEntity.title,
        overview = entity.movieEntity.overview,
        poster = entity.movieEntity.poster,
        backdrop = entity.movieEntity.backdrop,
        actors = entity.actors.map(::toActor),
        genres = entity.genres.map(::toGenre),
        minAge = entity.movieEntity.minAge,
        reviews = entity.movieEntity.reviews,
        rating = entity.movieEntity.rating,
        runtime = entity.movieEntity.runtime
    )

fun fromMovie(movie: Movie, actors: List<Actor>, genres: List<Genre>) =
    MovieWithActorsAndGenres(
        MovieDbEntity(
            movieId = movie.id,
            title = movie.title,
            overview = movie.overview,
            poster = movie.poster,
            backdrop = movie.backdrop,
            minAge = movie.minAge,
            reviews = movie.reviews,
            rating = movie.rating,
            runtime = movie.runtime
        ),
        actors = actors.map(::fromActor),
        genres = genres.map(::fromGenre)
    )