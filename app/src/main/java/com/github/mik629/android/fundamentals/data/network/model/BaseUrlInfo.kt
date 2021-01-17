package com.github.mik629.android.fundamentals.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseUrlInfo(
    @SerialName("base_url")
    val baseUrl: String,
    @SerialName("poster_sizes")
    val posterSizes: List<String>
)