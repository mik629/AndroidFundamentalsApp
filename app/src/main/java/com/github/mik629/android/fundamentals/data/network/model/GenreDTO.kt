package com.github.mik629.android.fundamentals.data.network.model

import com.github.mik629.android.fundamentals.domain.model.Genre
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreDTO(val id: Long, val name: String)

fun GenreDTO.toGenre() =
    Genre(
        id = this.id,
        name = this.name
    )