package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.github.mik629.android.fundamentals.domain.model.Genre
import com.github.mik629.android.fundamentals.domain.model.Movie

@Entity(primaryKeys = [MovieGenreCrossRef.COLUMN_MOVIE_ID, MovieGenreCrossRef.COLUMN_GENRE_ID])
data class MovieGenreCrossRef(
    @ColumnInfo(name = COLUMN_MOVIE_ID)
    val movieId: Long,
    @ColumnInfo(name = COLUMN_GENRE_ID)
    val genreId: Long
) {
    companion object {
        // rename columns to avoid extra renamings during migrations
        const val COLUMN_MOVIE_ID = "movie_id"
        const val COLUMN_GENRE_ID = "genre_id"
    }
}

fun Genre.toCrossRef(movie: Movie): MovieGenreCrossRef =
    MovieGenreCrossRef(
        movieId = movie.id,
        genreId = this.id
    )