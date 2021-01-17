package com.github.mik629.android.fundamentals.data.network

import com.github.mik629.android.fundamentals.BuildConfig
import com.github.mik629.android.fundamentals.data.network.model.ConfigurationResponse
import com.github.mik629.android.fundamentals.data.network.model.MovieActorsResponse
import com.github.mik629.android.fundamentals.data.network.model.MovieDetails
import com.github.mik629.android.fundamentals.data.network.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerApi {
    @GET("configuration")
    suspend fun getConfiguration(@Query("api_key") apiKey: String = BuildConfig.API_KEY): ConfigurationResponse

    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String = "popular",
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieDetails

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieActors(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieActorsResponse
}