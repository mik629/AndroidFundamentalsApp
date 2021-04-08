package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.github.mik629.android.fundamentals.domain.model.Genre
import com.github.mik629.android.fundamentals.domain.model.Movie

@Entity(
    primaryKeys = [MovieGenreCrossRef.COLUMN_MOVIE_ID, MovieGenreCrossRef.COLUMN_GENRE_ID],
    foreignKeys = [
        ForeignKey(
            entity = MovieDbEntity::class,
            parentColumns = [MovieDbEntity.COLUMN_MOVIE_ID],
            childColumns = [MovieGenreCrossRef.COLUMN_MOVIE_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenreDbEntity::class,
            parentColumns = [GenreDbEntity.COLUMN_GENRE_ID],
            childColumns = [MovieGenreCrossRef.COLUMN_GENRE_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
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