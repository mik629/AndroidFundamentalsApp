package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.data.db.models.GenreDbEntity.Companion.GENRES_TABLE_NAME
import com.github.mik629.android.fundamentals.domain.model.Genre

@Entity(tableName = GENRES_TABLE_NAME)
data class GenreDbEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_GENRE_ID)
    val genreId: Long,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String
) {
    companion object {
        const val GENRES_TABLE_NAME = "genres"

        const val COLUMN_GENRE_ID = "genre_id"
        const val COLUMN_NAME = "name"
    }
}

fun GenreDbEntity.toGenre(): Genre =
    Genre(
        id = this.genreId,
        name = this.name
    )

fun Genre.toEntity(): GenreDbEntity =
    GenreDbEntity(
        genreId = this.id,
        name = this.name
    )
