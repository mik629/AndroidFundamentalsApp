package com.github.mik629.android.fundamentals.data.background

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UpdateCacheWorker(
    context: Context,
    private val moviesRepository: MoviesRepository,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result =
        try {
            Timber.d("Running background task for cache update")
            moviesRepository.loadMoviesFromNetwork(category = "upcoming")
            Timber.d("End running background task for cache update")
            Result.success()
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure()
        }

    class Factory @Inject constructor(
        private val moviesRepository: MoviesRepository
    ) : WorkerFactory() {
        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): ListenableWorker =
            UpdateCacheWorker(
                context = appContext,
                workerParameters = workerParameters,
                moviesRepository = moviesRepository
            )
    }

    companion object {
        fun enqueueRequest(context: Context) {
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    "update_movies_cache",
                    ExistingPeriodicWorkPolicy.REPLACE,
                    PeriodicWorkRequest.Builder(UpdateCacheWorker::class.java, 1, TimeUnit.DAYS)
                        .setConstraints(
                            Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build()
                        ).build()
                )
        }
    }
}