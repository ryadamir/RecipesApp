package com.ryadamir.recipesapp.di

import com.ryadamir.recipesapp.viewmodels.HomeViewModel
import com.ryadamir.recipesapp.viewmodels.LoginViewModel
import com.ryadamir.recipesapp.viewmodels.SignUpViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * This is the viewmodel module to be instantiated in AppController
 */

val viewModelModule = module {

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        LoginViewModel()
    }

    viewModel {
        SignUpViewModel()
    }
}