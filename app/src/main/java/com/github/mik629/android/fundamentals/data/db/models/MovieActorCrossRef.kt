package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.github.mik629.android.fundamentals.data.db.models.ActorDbEntity.Companion.COLUMN_ACTOR_ID
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.COLUMN_MOVIE_ID
import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.domain.model.Movie

@Entity(primaryKeys = [COLUMN_MOVIE_ID, COLUMN_ACTOR_ID])
data class MovieActorCrossRef(
    @ColumnInfo(name = COLUMN_MOVIE_ID)
    val movieId: Long,
    @ColumnInfo(name = COLUMN_ACTOR_ID)
    val actorId: Long
)

fun Actor.toCrossRef(movie: Movie): MovieActorCrossRef =
    MovieActorCrossRef(
        movieId = movie.id,
        actorId = this.id
    )