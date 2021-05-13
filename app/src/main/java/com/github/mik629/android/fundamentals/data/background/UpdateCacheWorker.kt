package com.github.mik629.android.fundamentals.data.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.github.mik629.android.fundamentals.domain.repositories.MoviesRepository
import timber.log.Timber
import javax.inject.Inject

class UpdateCacheWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val moviesRepository: MoviesRepository
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
}