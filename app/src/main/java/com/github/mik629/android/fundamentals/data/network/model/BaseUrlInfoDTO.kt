package com.github.mik629.android.fundamentals.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseUrlInfoDTO(
    @Json(name = "secure_base_url")
    val baseUrl: String,
    @Json(name = "poster_sizes")
    val posterSizes: List<String>
)