package com.github.mik629.android.fundamentals.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("vote_average")
    val rating: Float,
    @SerialName("vote_count")
    val reviews: Int,
    @SerialName("genre_ids")
    val genres: List<Int>,
    @SerialName("adult")
    val isAdult: Boolean
)