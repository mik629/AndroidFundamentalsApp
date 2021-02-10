package com.github.mik629.android.fundamentals.data.network.model

import com.github.mik629.android.fundamentals.domain.model.Actor
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActorDTO(
    val id: Int,
    val name: String,
    @Json(name = "profile_path")
    val photoUrl: String?
)

fun ActorDTO.toActor(): Actor =
    Actor(
        id = this.id.toString(),
        name = this.name,
        photoUrl = this.photoUrl
    )