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
) {
    fun toMovie(): Movie =
        Movie(
            id = movieEntity.movieId,
            title = movieEntity.title,
            overview = movieEntity.overview,
            poster = movieEntity.posterImageUrl,
            backdrop = movieEntity.backdropImageUrl,
            actors = actors.map(ActorDbEntity::toActor),
            genres = genres.map(GenreDbEntity::toGenre),
            minAge = movieEntity.minAge,
            reviews = movieEntity.reviews,
            rating = movieEntity.rating,
            runtime = movieEntity.runtime
        )
}

fun fromMovie(movie: Movie, actors: List<Actor>, genres: List<Genre>) =
    MovieWithActorsAndGenres(
        MovieDbEntity(
            movieId = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterImageUrl = movie.poster,
            backdropImageUrl = movie.backdrop,
            minAge = movie.minAge,
            reviews = movie.reviews,
            rating = movie.rating,
            runtime = movie.runtime
        ),
        actors = actors.map(::fromActor),
        genres = genres.map(::fromGenre)
    )