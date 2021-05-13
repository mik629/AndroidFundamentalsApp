package com.github.mik629.android.fundamentals.di.modules

import android.app.Application
import com.github.mik629.android.fundamentals.App
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMovieDetails
import com.github.mik629.android.fundamentals.ui.movieslist.FragmentMoviesList
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class])
interface AppComponent {
    //    fun provideRouter(): Router

    fun inject(app: App)

    fun inject(fragment: FragmentMoviesList)

    fun inject(fragment: FragmentMovieDetails)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application
        ): AppComponent
    }
}
