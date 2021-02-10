package com.github.mik629.android.fundamentals.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropImageUrl: String?,
    val rating: Float,
    val reviews: Int,
    val genres: List<Genre>,
    val actors: List<Actor>,
    val minAge: Int,
    val runtime: Int
)
