package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Genres.COLUMN_NAME_GENRE_ID
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Genres.GENRES_TABLE_NAME

@Entity(tableName = GENRES_TABLE_NAME)
data class GenreDbEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_NAME_GENRE_ID)
    val genreId: Int,
    val name: String
)