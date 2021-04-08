package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.domain.model.Actor

@Entity(tableName = ActorDbEntity.TABLE_NAME)
data class ActorDbEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ACTOR_ID)
    val actorId: Long,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_PHOTO_URL)
    val photoUrl: String?
) {
    companion object {
        const val TABLE_NAME = "actors"

        const val COLUMN_ACTOR_ID = "actor_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHOTO_URL = "photo_url"
    }
}

fun ActorDbEntity.toActor(): Actor =
    Actor(
        id = this.actorId,
        name = this.name,
        photoUrl = this.photoUrl
    )

fun Actor.toEntity(): ActorDbEntity =
    ActorDbEntity(
        actorId = this.id,
        name = this.name,
        photoUrl = this.photoUrl
    )