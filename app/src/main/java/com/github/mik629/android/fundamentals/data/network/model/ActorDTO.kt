package com.github.mik629.android.fundamentals.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActorDTO(
    val id: Int,
    val name: String,
    @Json(name = "profile_path")
    val avaPath: String?
)