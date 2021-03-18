package com.github.mik629.android.fundamentals

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.github.mik629.android.fundamentals.di.AppComponent
import com.github.mik629.android.fundamentals.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var tree: Timber.Tree

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        Timber.plant(tree)
    }
}

val Activity.appComponent get() = (application as App).appComponent
val Fragment.appComponent get() = (requireActivity().application as App).appComponent
