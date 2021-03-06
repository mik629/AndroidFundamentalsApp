package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.model.MovieDetails

class MovieWithActorsAndGenres(
    @Embedded
    val movieEntity: MovieDbEntity,
    @Relation(
        parentColumn = MovieActorCrossRef.COLUMN_MOVIE_ID,
        entityColumn = MovieActorCrossRef.COLUMN_ACTOR_ID,
        associateBy = Junction(MovieActorCrossRef::class)
    )
    val actors: List<ActorDbEntity>,
    @Relation(
        parentColumn = MovieGenreCrossRef.COLUMN_MOVIE_ID,
        entityColumn = MovieGenreCrossRef.COLUMN_GENRE_ID,
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<GenreDbEntity>
)

fun MovieWithActorsAndGenres.toMovie(): Movie =
    Movie(
        id = this.movieEntity.movieId,
        title = this.movieEntity.title,
        posterUrl = this.movieEntity.posterImageUrl,
        genres = this.genres.map(GenreDbEntity::toGenre),
        minAge = this.movieEntity.minAge,
        reviews = this.movieEntity.reviews,
        rating = this.movieEntity.rating,
        runtime = this.movieEntity.runtime,
        details = MovieDetails(
            backdropImageUrl = movieEntity.backdropImageUrl,
            overview = movieEntity.overview,
            actors = this.actors.map(ActorDbEntity::toActor)
        )
    )
