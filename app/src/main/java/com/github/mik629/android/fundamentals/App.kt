package com.github.mik629.android.fundamentals

import android.app.Application
import com.github.mik629.android.fundamentals.di.AppComponent
import com.github.mik629.android.fundamentals.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var tree: Timber.Tree

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appContext(this)
            .apiUrl(BuildConfig.BASE_URL)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        Timber.plant(tree)
    }
}