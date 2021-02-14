package com.github.mik629.android.fundamentals.di

import android.content.Context
import com.github.mik629.android.fundamentals.App
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import com.github.mik629.android.fundamentals.ui.AppActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class])
interface AppComponent {
//    fun provideRouter(): Router

    fun provideMoviesRepository(): MoviesRepository

    fun inject(app: App)

    fun inject(appActivity: AppActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun apiUrl(@Named(NetworkModule.BASE_URL) url: String): Builder

        @BindsInstance
        fun appContext(@Named(AppModule.APP_CONTEXT) context: Context): Builder
    }
}
