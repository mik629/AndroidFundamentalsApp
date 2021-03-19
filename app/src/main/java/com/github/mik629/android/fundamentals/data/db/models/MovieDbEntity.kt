package com.github.mik629.android.fundamentals.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mik629.android.fundamentals.data.db.models.MovieDbEntity.Companion.MOVIES_TABLE_NAME
import com.github.mik629.android.fundamentals.domain.model.Movie

@Entity(tableName = MOVIES_TABLE_NAME)
data class MovieDbEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_MOVIE_ID)
    val movieId: Long,
    @ColumnInfo(name = COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = COLUMN_OVERVIEW)
    val overview: String,
    @ColumnInfo(name = COLUMN_POSTER_IMAGE_URL)
    val posterImageUrl: String?,
    @ColumnInfo(name = COLUMN_BACKDROP_IMAGE_URL)
    val backdropImageUrl: String?,
    @ColumnInfo(name = COLUMN_MIN_AGE)
    val minAge: Int,
    @ColumnInfo(name = COLUMN_REVIEWS)
    val reviews: Int,
    @ColumnInfo(name = COLUMN_RATING)
    val rating: Float,
    @ColumnInfo(name = COLUMN_RUNTIME)
    val runtime: Int
) {
    companion object {
        const val MOVIES_TABLE_NAME = "movies"

        const val COLUMN_MOVIE_ID = "movie_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_OVERVIEW = "overview"
        const val COLUMN_POSTER_IMAGE_URL = "poster_image_url"
        const val COLUMN_BACKDROP_IMAGE_URL = "backdrop_image_url"
        const val COLUMN_MIN_AGE = "min_age"
        const val COLUMN_REVIEWS = "reviews"
        const val COLUMN_RATING = "rating"
        const val COLUMN_RUNTIME = "runtime"
    }
}

fun Movie.toEntity(): MovieDbEntity =
    MovieDbEntity(
        movieId = this.id,
        title = this.title,
        overview = this.overview,
        posterImageUrl = this.posterUrl,
        backdropImageUrl = this.backdropImageUrl,
        minAge = this.minAge,
        reviews = this.reviews,
        rating = this.rating,
        runtime = this.runtime
    )