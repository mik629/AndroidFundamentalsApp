package com.github.mik629.android.fundamentals.di.modules

import android.app.Application
import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkRequest
import androidx.work.WorkerFactory
import com.github.mik629.android.fundamentals.data.background.UpdateCacheWorker
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
internal object AppModule {
    @Provides
    @Singleton
    fun provideAppContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideUpdateCacheWorkRequest(): WorkRequest =
        PeriodicWorkRequest.Builder(UpdateCacheWorker::class.java, 2, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()

    @Provides
    @Singleton
    fun provideUpdateCacheWorkerFactory(factory: UpdateCacheWorker.Factory): WorkerFactory =
        factory
}