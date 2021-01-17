package com.github.mik629.android.fundamentals.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    val page: Int,
    val results: List<Movie>
)