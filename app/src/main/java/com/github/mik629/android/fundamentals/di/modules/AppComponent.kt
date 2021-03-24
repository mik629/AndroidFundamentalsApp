package com.github.mik629.android.fundamentals.di.modules

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.github.mik629.android.fundamentals.App
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMovieDetails
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class])
interface AppComponent {
    //    fun provideRouter(): Router

    fun provideMoviesRepository(): MoviesRepository // fixme remove if not needed

    fun provideMoviesListViewModelFactory(): ViewModelProvider.Factory

    fun inject(app: App)

    fun inject(fragment: FragmentMovieDetails)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application
        ): AppComponent
    }
}
