package com.github.mik629.android.fundamentals.data.network

data class MovieItem(
    val title: String,
    val genres: List<String>,
    val minAge: Int,
    val reviews: Int,
    val rating: Float,
    val minutes: Int
)
