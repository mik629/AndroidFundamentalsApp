package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.github.mik629.android.fundamentals.data.db.models.GenreDbEntity.Companion.COLUMN_GENRE_ID
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.COLUMN_MOVIE_ID
import com.github.mik629.android.fundamentals.domain.model.Genre
import com.github.mik629.android.fundamentals.domain.model.Movie

@Entity(primaryKeys = [COLUMN_MOVIE_ID, COLUMN_GENRE_ID])
data class MovieGenreCrossRef(
    @ColumnInfo(name = COLUMN_MOVIE_ID)
    val movieId: Long,
    @ColumnInfo(name = COLUMN_GENRE_ID)
    val genreId: Long
)

fun Genre.toCrossRef(movie: Movie): MovieGenreCrossRef =
    MovieGenreCrossRef(
        movieId = movie.id,
        genreId = this.id
    )