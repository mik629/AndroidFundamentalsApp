package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Genres.COLUMN_NAME_GENRE_ID
import com.github.mik629.android.fundamentals.data.db.contracts.DbContract.Movies.COLUMN_NAME_MOVIE_ID

@Entity
data class MovieGenreCrossRef(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ForeignKey(
        entity = MovieDbEntity::class,
        parentColumns = [COLUMN_NAME_MOVIE_ID],
        childColumns = [COLUMN_NAME_MOVIE_ID]
    )
    val movieId: Int,
    @ForeignKey(
        entity = GenreDbEntity::class,
        parentColumns = [COLUMN_NAME_GENRE_ID],
        childColumns = [COLUMN_NAME_GENRE_ID]
    )
    val genreId: Int
)