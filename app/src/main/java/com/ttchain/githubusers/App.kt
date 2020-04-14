package com.ttchain.githubusers

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.ttchain.githubusers.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    companion object {
        var apiAddress = "http://18.177.24.213:63339/"

        lateinit var instance: App
    }

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Koin
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(appModule)
        }
    }
}