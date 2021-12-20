package com.sapar.breakingbadapplication.Registration

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class DBHelper(
    context: Context,
) : SQLiteOpenHelper(context, dbname, factory, version) {

    companion object {
        internal val dbname = "Login.db"
        internal val factory = null
        internal val version = 1
    }

    override fun onCreate(MyDB: SQLiteDatabase?) {
//                MyDB.execSQL("create table users(email TEXT primary key, password TEXT)");
        MyDB?.execSQL("create table users(email TEXT primary key, name TEXT, password TEXT)")
    }

    override fun onUpgrade(MyDB: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        MyDB?.execSQL("drop table if exists users")
//                MyDB.execSQL("drop table if exists users");
    }

    fun insertData(email: String?,name: String?, password: String?): Boolean {
        val db:SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("email", email)
        contentValues.put("name", name)
        contentValues.put("password", password)
        val result: Long = db.insert("users", null, contentValues)
        return result != (-1).toLong()
    }



    @SuppressLint("Recycle")
    fun checkUsersEmail(email: String): Boolean {
        val db:SQLiteDatabase = this.writableDatabase
        val cursor = db.rawQuery("select * from users where email = ?", arrayOf(email))
        return cursor.count > 0
    }


    @SuppressLint("Recycle")
    fun checkUsersPassword(email: String, password: String): Boolean {
        val db = this.writableDatabase
        val cursor = db.rawQuery(
            "select * from users where email = ? and password = ? ",
            arrayOf(email, password)
        )
        return cursor.count > 0
    }

    @SuppressLint("Recycle", "Range")
    fun readData(email: String): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from users where email = ?", arrayOf(email))
        cursor.moveToFirst()
        if (cursor.count > 0){
            return cursor.getString(cursor.getColumnIndex("name"))
        }
        return null
    }
}