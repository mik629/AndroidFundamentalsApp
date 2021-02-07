package com.github.mik629.android.fundamentals.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.mik629.android.fundamentals.BuildConfig
import com.github.mik629.android.fundamentals.GlideApp
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.data.db.MovieDb
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.github.mik629.android.fundamentals.data.repositories.MoviesRepositoryImpl
import com.github.mik629.android.fundamentals.ui.movieslist.MoviesListViewModel
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AppModule private constructor() {
    val retrofit: Retrofit
    var viewModel: MoviesListViewModel? = null

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okhttp = OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .client(okhttp)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
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

    fun provideViewModel(context: Context): MoviesListViewModel {
        if (viewModel == null) {
            viewModel = MoviesListViewModel(
                MoviesRepositoryImpl(
                    retrofit.create(ServerApi::class.java),
                    MovieDb.createDb(context).dao
                )
            )
        }
        return viewModel!!
    }

    companion object {
        val instance: AppModule = AppModule()
    }
}

fun buildGlideRequest(fragment: Fragment) =
    GlideApp.with(fragment)
        .asDrawable()
        .thumbnail(0.1f)
        .placeholder(R.drawable.ic_image_loading)
        .fallback(R.drawable.ic_broken_image)
        .transition(DrawableTransitionOptions.withCrossFade())
        .fitCenter()
