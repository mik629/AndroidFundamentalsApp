package com.github.mik629.android.fundamentals.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val avaPath: String
)