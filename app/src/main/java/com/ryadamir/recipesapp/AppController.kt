package com.ryadamir.recipesapp

import android.app.Application
import com.ryadamir.recipesapp.di.dataModule
import com.ryadamir.recipesapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * This is a class that extends Application class to instantiate the Koin framework (for dependency injection) with the other modules
 */

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppController)
            modules(dataModule)
            modules(viewModelModule)
        }
    }
}