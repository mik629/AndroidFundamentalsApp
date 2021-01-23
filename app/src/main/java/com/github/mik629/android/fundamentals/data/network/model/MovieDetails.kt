package com.github.mik629.android.fundamentals.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetails(
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
    val genres: List<Genre>,
    @Json(name = "adult")
    val isAdult: Boolean,
    val runtime: Int
)