package com.github.mik629.android.fundamentals.di

import android.app.Application
import com.github.mik629.android.fundamentals.App
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import com.github.mik629.android.fundamentals.ui.AppActivity
import com.github.mik629.android.fundamentals.ui.BaseFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, CommonUiModule::class, DataModule::class, NetworkModule::class, ViewModelsModule::class])
interface AppComponent {
    //    fun provideRouter(): Router

    fun provideMoviesRepository(): MoviesRepository

    fun inject(app: App)

    fun inject(appActivity: AppActivity)

    fun inject(fragment: BaseFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application
        ): AppComponent
    }
}
