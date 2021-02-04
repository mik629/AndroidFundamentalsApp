package com.github.mik629.android.fundamentals.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String?,
    val backdrop: String?,
    val actors: List<Actor>,
    val genres: List<Genre>,
    val minAge: Int,
    val reviews: Int,
    val rating: Float,
    val runtime: Int
) : Parcelable
