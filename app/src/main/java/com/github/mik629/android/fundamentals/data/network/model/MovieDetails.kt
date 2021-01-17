package com.github.mik629.android.fundamentals.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("vote_average")
    val rating: Float,
    @SerialName("vote_count")
    val reviews: Int,
    val genres: List<Genre>,
    @SerialName("adult")
    val isAdult: Boolean,
    val runtime: Int
)