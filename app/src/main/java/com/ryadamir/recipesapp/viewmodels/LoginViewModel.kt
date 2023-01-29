package com.ryadamir.recipesapp.viewmodels

import androidx.lifecycle.ViewModel
import com.ryadamir.recipesapp.source.local.DBHelper
import com.ryadamir.recipesapp.util.SharedPrefManager

/**
 * This is the viewmodel for the Login activity
 */

class LoginViewModel() : ViewModel() {

    fun login(user: String, pass: String, dbHelper: DBHelper): Boolean {
        return dbHelper.checkuserNamePassword(user, pass);
    }

    fun isUserLoggedIn(session: SharedPrefManager): Boolean {
        return session.isLoggedIn
    }

    fun logUser(user: String, pass: String, session: SharedPrefManager) {
        session.userLogin(user, pass)
    }

}