package com.ryadamir.recipesapp.di

import com.ryadamir.recipesapp.data.Repository
import com.ryadamir.recipesapp.source.remote.RetrofitClient
import org.koin.dsl.module

/**
 * This is the data module to be instantiated in AppController
 */

val dataModule = module {
    single {
        RetrofitClient.instance
    }

    factory {
        Repository(get())
    }
}