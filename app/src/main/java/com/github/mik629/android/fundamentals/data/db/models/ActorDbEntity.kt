package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.data.db.models.ActorDbEntity.Companion.ACTORS_TABLE_NAME
import com.github.mik629.android.fundamentals.domain.model.Actor

@Entity(tableName = ACTORS_TABLE_NAME)
data class ActorDbEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_NAME_ACTOR_ID)
    val actorId: Long,
    val name: String,
    val photoUrl: String?
) {
    companion object {
        const val ACTORS_TABLE_NAME = "actors"

        const val COLUMN_NAME_ACTOR_ID = "actorId"
    }
}

fun ActorDbEntity.toActor(): Actor =
    Actor(
        id = this.actorId,
        name = this.name,
        photoUrl = this.photoUrl
    )

fun Actor.toEntity() =
    ActorDbEntity(
        actorId = this.id,
        name = this.name,
        photoUrl = this.photoUrl
    )