package com.github.mik629.android.fundamentals.di

import com.github.mik629.android.fundamentals.BuildConfig
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor())
            .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(
        @Named(BASE_URL) baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
    }

    @Provides
    @Singleton
    internal fun provideApi(retrofit: Retrofit): ServerApi = retrofit.create(ServerApi::class.java)

    companion object {
        const val BASE_URL = "baseUrl"
    }
}

private class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}