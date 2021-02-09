package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.data.db.models.GenreDbEntity.Companion.COLUMN_NAME_GENRE_ID
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.COLUMN_NAME_MOVIE_ID

@Entity
data class MovieGenreCrossRef(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ForeignKey(
        entity = MovieDbEntity::class,
        parentColumns = [COLUMN_NAME_MOVIE_ID],
        childColumns = [COLUMN_NAME_MOVIE_ID]
    )
    val movieId: String,
    @ForeignKey(
        entity = GenreDbEntity::class,
        parentColumns = [COLUMN_NAME_GENRE_ID],
        childColumns = [COLUMN_NAME_GENRE_ID]
    )
    val genreId: String
)