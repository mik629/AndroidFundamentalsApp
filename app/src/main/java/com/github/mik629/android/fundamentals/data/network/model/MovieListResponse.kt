package com.github.mik629.android.fundamentals.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieListResponse(
    val page: Int,
    val results: List<MovieDTO>
)