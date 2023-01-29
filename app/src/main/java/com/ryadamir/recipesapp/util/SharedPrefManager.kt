package com.ryadamir.recipesapp.util

import android.content.Context

/**
 * This is a class to manage shared preferences of the user in the app
 */

class SharedPrefManager(context: Context?) {
    init {
        Companion.context = context
    }

    // to check if a user is logged in
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences =
                context!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY_LOGIN, null) != null
        }

    // to logout a user and clear shared preferences
    fun logout(): Boolean {
        val sharedPreferences =
            context!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        editor.commit()
        return true
    }

    // login a user and set data to shared preferences
    fun userLogin(
        login: String?,
        passe: String?,
    ): Boolean {
        val sharedPreferences =
            context!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_LOGIN, login)
        editor.putString(KEY_PASS, passe)
        editor.commit()
        return true
    }

    companion object {
        private var mInstance: SharedPrefManager? = null
        private var context: Context? = null
        private const val SHARED_PREF_NAME = "recipesapp"
        private val KEY_LOGIN = null
        private val KEY_PASS = null

        @Synchronized
        fun getInstance(context: Context?): SharedPrefManager? {
            if (mInstance == null) {
                mInstance = SharedPrefManager(context)
            }
            return mInstance
        }
    }
}