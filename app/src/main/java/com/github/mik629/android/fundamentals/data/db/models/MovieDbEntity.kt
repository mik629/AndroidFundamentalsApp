package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.MOVIES_TABLE_NAME

@Entity(tableName = MOVIES_TABLE_NAME)
data class MovieDbEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_NAME_MOVIE_ID)
    val movieId: String,
    val title: String,
    val overview: String,
    val posterImageUrl: String?,
    val backdropImageUrl: String?,
    val minAge: Int,
    val reviews: Int,
    val rating: Float,
    val runtime: Int
) {
    companion object {
        const val MOVIES_TABLE_NAME = "movies"

        const val COLUMN_NAME_RATING = "rating"

        const val COLUMN_NAME_MOVIE_ID = "movieId"
    }
}