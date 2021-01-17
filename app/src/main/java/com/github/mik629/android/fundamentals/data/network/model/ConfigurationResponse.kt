package com.github.mik629.android.fundamentals.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    @SerialName("images")
    val baseUrlInfo: BaseUrlInfo
)