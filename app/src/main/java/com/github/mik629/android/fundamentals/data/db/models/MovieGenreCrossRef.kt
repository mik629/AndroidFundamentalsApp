package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.Entity
import com.github.mik629.android.fundamentals.data.db.models.GenreDbEntity.Companion.COLUMN_NAME_GENRE_ID
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.COLUMN_NAME_MOVIE_ID
import com.github.mik629.android.fundamentals.domain.model.Genre
import com.github.mik629.android.fundamentals.domain.model.Movie

@Entity(primaryKeys = [COLUMN_NAME_MOVIE_ID, COLUMN_NAME_GENRE_ID])
data class MovieGenreCrossRef(
    val movieId: String,
    val genreId: String
)

fun Genre.toCrossRef(movie: Movie): MovieGenreCrossRef =
    MovieGenreCrossRef(
        movieId = movie.id,
        genreId = id
    )