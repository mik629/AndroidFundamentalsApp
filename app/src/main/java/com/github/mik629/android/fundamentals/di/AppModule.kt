package com.github.mik629.android.fundamentals.di

import android.content.Context
import dagger.Module
import dagger.Provides
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(@Named(APP_CONTEXT) context: Context): Context = context

    @Provides
    @Singleton
    fun provideTimberTree(): Timber.Tree =
        Timber.DebugTree()

    companion object {
        const val APP_CONTEXT = "appcontext"
    }
}
