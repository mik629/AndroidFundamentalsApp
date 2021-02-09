package com.github.mik629.android.fundamentals.data.network.model

import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsDTO(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "vote_average")
    val rating: Float,
    @Json(name = "vote_count")
    val reviews: Int,
    val genres: List<GenreDTO>,
    @Json(name = "adult")
    val isAdult: Boolean,
    val runtime: Int
) {
    fun toMovie(actors: List<Actor>) =
        Movie(
            id = id.toString(),
            title = title,
            overview = overview,
            posterUrl = posterPath,
            backdropImageUrl = backdropPath,
            actors = actors,
            genres = genres.map(::toGenre),
            minAge = if (isAdult) 18 else 0,
            reviews = reviews,
            rating = rating,
            runtime = runtime
        )
}