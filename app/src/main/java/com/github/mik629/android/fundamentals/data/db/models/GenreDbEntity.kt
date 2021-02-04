package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.data.db.models.GenreDbEntity.Companion.GENRES_TABLE_NAME
import com.github.mik629.android.fundamentals.domain.model.Genre

@Entity(tableName = GENRES_TABLE_NAME)
data class GenreDbEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_NAME_GENRE_ID)
    val genreId: Int,
    val name: String
) {
    companion object {
        const val COLUMN_NAME_GENRE_ID = "genreId"

        const val GENRES_TABLE_NAME = "genres"
    }
}

fun toGenre(entity: GenreDbEntity) =
    Genre(
        id = entity.genreId,
        name = entity.name
    )

fun fromGenre(genre: Genre) =
    GenreDbEntity(
        genreId = genre.id,
        name = genre.name
    )