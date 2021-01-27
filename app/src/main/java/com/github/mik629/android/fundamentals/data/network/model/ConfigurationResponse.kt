package com.github.mik629.android.fundamentals.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfigurationResponse(
    @Json(name = "images")
    val baseUrlInfo: BaseUrlInfo
)