package com.github.mik629.android.fundamentals.di

import com.github.mik629.android.fundamentals.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class AppModule {
    val retrofit: Retrofit

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okhttp = OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()

        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(okhttp)
            .build()
    }
}