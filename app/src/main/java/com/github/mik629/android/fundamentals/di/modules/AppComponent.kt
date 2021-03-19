package com.github.mik629.android.fundamentals.di.modules

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.github.mik629.android.fundamentals.App
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import com.github.mik629.android.fundamentals.ui.AppActivity
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMovieDetails
import com.github.mik629.android.fundamentals.ui.movieslist.FragmentMoviesList
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class])
interface AppComponent {
    //    fun provideRouter(): Router

    fun provideMoviesRepository(): MoviesRepository

    fun provideMoviesListViewModelFactory(): ViewModelProvider.Factory

    fun inject(app: App)

    fun inject(appActivity: AppActivity)

    fun inject(fragment: FragmentMovieDetails)

    fun inject(fragment: FragmentMoviesList)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application
        ): AppComponent
    }
}
