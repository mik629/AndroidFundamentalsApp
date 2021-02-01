package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Actors.ACTORS_TABLE_NAME
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Actors.COLUMN_NAME_ACTOR_ID

@Entity(tableName = ACTORS_TABLE_NAME)
data class ActorDbEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_NAME_ACTOR_ID)
    val actorId: Int,
    val name: String,
    val ava: String?
)