package com.github.mik629.android.fundamentals.data.network.model

data class MovieItem(
    val title: String,
    val poster: String,
    val genres: List<String>,
    val minAge: Int,
    val reviews: Int,
    val rating: Float,
    val minutes: Int
)
