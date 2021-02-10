package com.github.mik629.android.fundamentals.data.network

import com.github.mik629.android.fundamentals.data.network.model.ConfigurationResponse
import com.github.mik629.android.fundamentals.data.network.model.MovieActorsResponse
import com.github.mik629.android.fundamentals.data.network.model.MovieDetailsDTO
import com.github.mik629.android.fundamentals.data.network.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerApi {
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse

    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String = "popular"
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String = "en-US"
    ): MovieDetailsDTO

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieActors(
        @Path("movie_id") movieId: Long
    ): MovieActorsResponse
}