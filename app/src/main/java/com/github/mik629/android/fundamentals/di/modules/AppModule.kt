package com.github.mik629.android.fundamentals.di.modules

import android.app.Application
import android.content.Context
import androidx.work.WorkerFactory
import com.github.mik629.android.fundamentals.data.background.UpdateCacheWorker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object AppModule {
    @Provides
    @Singleton
    fun provideAppContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideUpdateCacheWorkerFactory(factory: UpdateCacheWorker.Factory): WorkerFactory =
        factory
}