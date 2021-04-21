package com.github.mik629.android.fundamentals.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val posterUrl: String?,
    val rating: Float,
    val reviews: Int,
    val genres: List<Genre>,
    val minAge: Int,
    val runtime: Int,
    val details: MovieDetails
)
