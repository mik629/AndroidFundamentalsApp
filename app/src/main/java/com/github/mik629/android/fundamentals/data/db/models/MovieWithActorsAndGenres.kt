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

fun MovieWithActorsAndGenres.toMovie(): Movie =
    Movie(
        id = this.movieEntity.movieId,
        title = this.movieEntity.title,
        overview = this.movieEntity.overview,
        posterUrl = this.movieEntity.posterImageUrl,
        backdropImageUrl = this.movieEntity.backdropImageUrl,
        actors = this.actors.map(ActorDbEntity::toActor),
        genres = this.genres.map(GenreDbEntity::toGenre),
        minAge = this.movieEntity.minAge,
        reviews = this.movieEntity.reviews,
        rating = this.movieEntity.rating,
        runtime = this.movieEntity.runtime
    )

fun Movie.toComplexEntity() =
    MovieWithActorsAndGenres(
        MovieDbEntity(
            movieId = this.id,
            title = this.title,
            overview = this.overview,
            posterImageUrl = this.posterUrl,
            backdropImageUrl = this.backdropImageUrl,
            minAge = this.minAge,
            reviews = this.reviews,
            rating = this.rating,
            runtime = this.runtime
        ),
        actors = this.actors.map(Actor::toEntity),
        genres = this.genres.map(Genre::toEntity)
    )