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
    val actorId: String,
    val name: String,
    val photoUrl: String?
) {
    companion object {
        const val ACTORS_TABLE_NAME = "actors"

        const val COLUMN_NAME_ACTOR_ID = "actorId"
    }
}

fun toActor(entity: ActorDbEntity) =
    Actor(
        id = entity.actorId,
        name = entity.name,
        photoUrl = entity.photoUrl
    )

fun fromActor(actor: Actor) =
    ActorDbEntity(
        actorId = actor.id,
        name = actor.name,
        photoUrl = actor.photoUrl
    )