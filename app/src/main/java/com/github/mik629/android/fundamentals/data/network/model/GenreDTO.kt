package com.github.mik629.android.fundamentals.data.network.model

import com.github.mik629.android.fundamentals.domain.model.Genre
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreDTO(val id: Int, val name: String)

fun toGenre(dto: GenreDTO) =
    Genre(
        id = dto.id,
        name = dto.name
    )