package com.sapar.breakingbadapplication.Registration

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
        internal val dbname = "userDB"
        internal val factory = null
        internal val version = 1
    }

    override fun onCreate(MyDB: SQLiteDatabase?) {
        MyDB?.execSQL("create table users(email TEXT primary key, name TEXT, password TEXT)")
    }

    override fun onUpgrade(MyDB: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        MyDB?.execSQL("drop table if exists users")
    }

    fun insertData(name: String?, email: String?, password: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("email", email)
        contentValues.put("name", name)
        contentValues.put("password", password)
        val result: Long = db.insert("users", null, contentValues)
        return result != 1.toLong()
    }

    fun checkUsersEmail(email: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery("select * from users where email = ?", arrayOf(email))
        return cursor.count > 0
    }

    fun checkUsersPassword(email: String, password: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery(
            "select * from users where email = ? and password = ? ",
            arrayOf(email, password)
        )
        return cursor.count > 0
    }

    fun readData(email: String): User? {
//        val MyDB = this.writableDatabase
//        val cursor = MyDB.rawQuery(
//            "select * from users where email = ?",
//            arrayOf(email)
//        )
//        if (cursor.count > 0){
//            return cursor.toString()
//        }
//        return null
        val selectQuery = "select * from users where email = $email"
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from users where email = ?", arrayOf(email))
        cursor.moveToFirst()
        if (cursor.count > 0){
            User(cursor.getString(1), cursor.getString(0))
        }
        return null
    }
}