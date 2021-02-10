package com.github.mik629.android.fundamentals.domain.model

data class MovieDetails(
    val title: String,
    val background: String?,
    val storyline: String,
    val genres: List<String>,
    val actors: List<Actor>,
    val minAge: Int,
    val reviews: Int,
    val rating: Float
)