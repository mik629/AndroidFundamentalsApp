package com.github.mik629.android.fundamentals.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "vote_average")
    val rating: Float,
    @Json(name = "vote_count")
    val reviews: Int,
    @Json(name = "genre_ids")
    val genres: List<Int>,
    @Json(name = "adult")
    val isAdult: Boolean
)