package com.example.swipeandroidassignment

import android.app.Application
import com.example.swipeandroidassignment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication:Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@BaseApplication)
            modules(appModule)
        }
    }


}