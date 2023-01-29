package com.ryadamir.recipesapp.viewmodels

import androidx.lifecycle.ViewModel
import com.ryadamir.recipesapp.source.local.DBHelper
import com.ryadamir.recipesapp.util.SharedPrefManager

/**
 * This is the viewmodel for the sign up activity
 */

class SignUpViewModel() : ViewModel() {

    fun insert(user: String, pass: String, dbHelper: DBHelper): Boolean {
        return dbHelper.insertData(user, pass)
    }

    fun checkUsername(user: String, dbHelper: DBHelper): Boolean {
        return dbHelper.checkUsername(user)
    }

    fun logUser(user: String, pass: String, session: SharedPrefManager) {
        session.userLogin(user, pass)
    }

}