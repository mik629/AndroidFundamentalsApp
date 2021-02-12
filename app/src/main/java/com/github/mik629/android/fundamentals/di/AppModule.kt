package com.github.mik629.android.fundamentals.di

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.mik629.android.fundamentals.BuildConfig
import com.github.mik629.android.fundamentals.GlideApp
import com.github.mik629.android.fundamentals.GlideRequest
import com.github.mik629.android.fundamentals.R
import com.github.mik629.android.fundamentals.data.MoviesLoader
import com.github.mik629.android.fundamentals.data.db.MovieDb
import com.github.mik629.android.fundamentals.data.db.daos.MovieDao
import com.github.mik629.android.fundamentals.data.network.ServerApi
import com.github.mik629.android.fundamentals.data.repositories.MoviesRepositoryImpl
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import com.github.mik629.android.fundamentals.ui.moviedetails.MovieDetailsViewModel
import com.github.mik629.android.fundamentals.ui.movieslist.MoviesListViewModel
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class AppModule private constructor(applicationContext: Context) {
    val retrofit: Retrofit by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okhttp = OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor())
            .build()
        Retrofit.Builder()
            .client(okhttp)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
    }
    val dao: MovieDao by lazy {
        MovieDb.createDb(applicationContext).dao
    }
    val movieLoader: MoviesLoader by lazy {
        MoviesLoader(
            retrofit.create(ServerApi::class.java),
            dao
        )
    }
    val moviesRepository: MoviesRepository by lazy {
        MoviesRepositoryImpl(movieLoader, dao)
    }

    init {
        Timber.plant(Timber.DebugTree())
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

    fun provideMovieListViewModel(): MoviesListViewModel =
        MoviesListViewModel(moviesRepository)

    fun provideMovieDetailsViewModel(id: Long): MovieDetailsViewModel =
        MovieDetailsViewModel(
            moviesRepository,
            id
        )

    companion object {
        var instance: AppModule? = null

        fun getInstance(context: Context): AppModule {
            if (instance == null) {
                instance = AppModule(context)
            }
            return instance!!
        }
    }
}

fun buildGlideRequest(fragment: Fragment): GlideRequest<Drawable> =
    GlideApp.with(fragment)
        .asDrawable()
        .thumbnail(0.1f)
        .placeholder(R.drawable.ic_image_loading)
        .fallback(R.drawable.ic_broken_image)
        .transition(DrawableTransitionOptions.withCrossFade())
        .fitCenter()
