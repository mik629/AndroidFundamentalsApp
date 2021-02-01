package com.github.mik629.android.fundamentals.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieItem(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String?,
    val backdrop: String?,
    val actors: List<ActorItem>,
    val genres: List<GenreItem>,
    val minAge: Int,
    val reviews: Int,
    val rating: Float,
    val runtime: Int
) : Parcelable