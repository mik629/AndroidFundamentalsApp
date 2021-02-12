package com.github.mik629.android.fundamentals

import android.app.Application
import com.github.mik629.android.fundamentals.di.AppModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appModule = AppModule.getInstance(applicationContext)
    }

    companion object {
        lateinit var appModule: AppModule
    }
}