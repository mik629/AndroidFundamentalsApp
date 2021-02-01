package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Movies.COLUMN_NAME_MOVIE_ID
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Movies.MOVIES_TABLE_NAME

@Entity(tableName = MOVIES_TABLE_NAME)
data class MovieDbEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_NAME_MOVIE_ID)
    val movieId: Int,
    val title: String,
    val overview: String,
    val poster: String?,
    val backdrop: String?,
    val minAge: Int,
    val reviews: Int,
    val rating: Float,
    val runtime: Int
)