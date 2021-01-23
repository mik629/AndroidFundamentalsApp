package com.github.mik629.android.fundamentals.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(val id: Int, val name: String)