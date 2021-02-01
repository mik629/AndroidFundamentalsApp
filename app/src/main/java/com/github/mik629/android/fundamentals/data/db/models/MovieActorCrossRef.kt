package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.data.db.models.ActorDbEntity.Companion.COLUMN_NAME_ACTOR_ID
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.COLUMN_NAME_MOVIE_ID

@Entity
data class MovieActorCrossRef(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ForeignKey(
        entity = MovieDbEntity::class,
        parentColumns = [COLUMN_NAME_MOVIE_ID],
        childColumns = [COLUMN_NAME_MOVIE_ID]
    )
    val movieId: Int,
    @ForeignKey(
        entity = ActorDbEntity::class,
        parentColumns = [COLUMN_NAME_ACTOR_ID],
        childColumns = [COLUMN_NAME_ACTOR_ID]
    )
    val actorId: Int
)