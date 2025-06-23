package com.darkjesus.shakeit.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ShakeIt : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShakeIt)
            modules(appModule)
        }
    }
}