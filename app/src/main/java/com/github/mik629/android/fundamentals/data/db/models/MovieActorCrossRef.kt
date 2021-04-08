package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.domain.model.Movie

@Entity(
    primaryKeys = [MovieActorCrossRef.COLUMN_MOVIE_ID, MovieActorCrossRef.COLUMN_ACTOR_ID],
    foreignKeys = [
        ForeignKey(
            entity = MovieDbEntity::class,
            parentColumns = [MovieDbEntity.COLUMN_MOVIE_ID],
            childColumns = [MovieActorCrossRef.COLUMN_MOVIE_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ActorDbEntity::class,
            parentColumns = [ActorDbEntity.COLUMN_ACTOR_ID],
            childColumns = [MovieActorCrossRef.COLUMN_ACTOR_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class MovieActorCrossRef(
    @ColumnInfo(name = COLUMN_MOVIE_ID)
    val movieId: Long,
    @ColumnInfo(name = COLUMN_ACTOR_ID)
    val actorId: Long
) {
    companion object {
        const val COLUMN_MOVIE_ID = "movie_id"
        const val COLUMN_ACTOR_ID = "actor_id"
    }
}

fun Actor.toCrossRef(movie: Movie): MovieActorCrossRef =
    MovieActorCrossRef(
        movieId = movie.id,
        actorId = this.id
    )