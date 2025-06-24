package com.darkjesus.shakeit.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

/**
 * Main Application class for the ShakeIt app.
 *
 * This class initializes the Koin dependency injection framework
 * and sets up the application-wide dependencies.
 */
class ShakeIt : Application() {
    /**
     * Called when the application is starting.
     *
     * Initializes the Koin dependency injection framework with the application context
     * and loads the application module containing all dependencies.
     */
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShakeIt)
            modules(appModule)
        }
    }
}