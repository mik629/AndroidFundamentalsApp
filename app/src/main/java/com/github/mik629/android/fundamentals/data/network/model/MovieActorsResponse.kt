package com.github.mik629.android.fundamentals.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieActorsResponse(
    val id: Int,
    val cast: List<Actor>
)