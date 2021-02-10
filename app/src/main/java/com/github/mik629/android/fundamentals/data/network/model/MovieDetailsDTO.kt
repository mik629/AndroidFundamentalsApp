package com.github.mik629.android.fundamentals.data.network.model

import com.github.mik629.android.fundamentals.domain.model.Actor
import com.github.mik629.android.fundamentals.domain.model.Movie
import com.github.mik629.android.fundamentals.domain.model.MovieDetails
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsDTO(
    val id: Long,
    val title: String,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "vote_average")
    val rating: Float,
    @Json(name = "vote_count")
    val reviews: Int,
    val genres: List<GenreDTO>,
    @Json(name = "adult")
    val isAdult: Boolean,
    val runtime: Int
)

fun MovieDetailsDTO.toMovie(actors: List<Actor>) =
    Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterPath,
        backdropImageUrl = this.backdropPath,
        actors = actors,
        genres = this.genres.map(GenreDTO::toGenre),
        minAge = if (this.isAdult) 18 else 0, // fixme determine age appropriately
        reviews = this.reviews,
        rating = this.rating,
        runtime = this.runtime
    )

fun MovieDetailsDTO.toMovieDetails(actors: List<Actor>) =
    MovieDetails(
        title = this.title,
        background = this.backdropPath,
        storyline = this.overview,
        genres = this.genres.map { it.name },
        actors = actors,
        minAge = if (this.isAdult) 18 else 0, // fixme determine age appropriately
        reviews = this.reviews,
        rating = this.rating
    )