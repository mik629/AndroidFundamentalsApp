package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.Entity
import com.github.mik629.android.fundamentals.data.db.models.ActorDbEntity.Companion.COLUMN_NAME_ACTOR_ID
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.COLUMN_NAME_MOVIE_ID
import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.domain.model.Movie

@Entity(primaryKeys = [COLUMN_NAME_MOVIE_ID, COLUMN_NAME_ACTOR_ID])
data class MovieActorCrossRef(
    val movieId: String,
    val actorId: String
)

fun Actor.toCrossRef(movie: Movie): MovieActorCrossRef =
    MovieActorCrossRef(
        movieId = movie.id,
        actorId = id
    )