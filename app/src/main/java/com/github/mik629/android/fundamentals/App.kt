package com.github.mik629.android.fundamentals

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import androidx.work.Configuration
import androidx.work.WorkerFactory
import com.github.mik629.android.fundamentals.di.modules.AppComponent
import com.github.mik629.android.fundamentals.di.modules.DaggerAppComponent
import com.github.mik629.android.fundamentals.ui.movieslist.MoviesListViewModel
import timber.log.Timber
import javax.inject.Inject

class App : Application(), Configuration.Provider {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(this)
    }

    @Inject
    lateinit var updateCacheWorkerFactory: WorkerFactory

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        Timber.plant(Timber.DebugTree())
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(updateCacheWorkerFactory)
            .build()
}

val Activity.appComponent get(): AppComponent = (application as App).appComponent
val Fragment.appComponent get(): AppComponent = requireActivity().appComponent
