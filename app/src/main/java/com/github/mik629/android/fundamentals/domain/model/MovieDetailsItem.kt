package com.github.mik629.android.fundamentals.domain.model

data class MovieDetailsItem(
    val title: String,
    val background: String,
    val storyline: String,
    val genres: List<String>,
    val actors: List<ActorItem>,
    val minAge: Int,
    val reviews: Int,
    val rating: Float
)