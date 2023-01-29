package com.ryadamir.recipesapp.source.local

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context

/**
 * This is a util class the local database management using SQLite
 */

class DBHelper(context: Context?) : SQLiteOpenHelper(context, "login.db", null, 1) {

    // creating the database
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL("create table users (id integer primary key autoincrement, username TEXT, password TEXT)")
    }

    // deleting the table if it exists to upgrade it
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        sqLiteDatabase.execSQL("drop table if exists users")
    }

    // inserting user in the database
    fun insertData(username: String?, password: String?): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)
        val result = db.insert("users", null, values)
        return result != -1L
    }

    // checking if a username already exists
    fun checkUsername(username: String): Boolean {
        val db = this.writableDatabase
        val cursor = db.rawQuery("select * from users where username=?", arrayOf(username))
        return cursor.count > 0
    }

    // checking the user to login
    fun checkuserNamePassword(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val cursor = db.rawQuery(
            "select * from users where username=? and password=?",
            arrayOf(username, password)
        )
        return cursor.count > 0
    }

    companion object {
        const val DBNAME = "login.db"
    }
}