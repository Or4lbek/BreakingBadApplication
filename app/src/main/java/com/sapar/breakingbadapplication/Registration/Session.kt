package com.sapar.breakingbadapplication.Registration

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
class Session(ctx:Context) {

    companion object{
        const val LOGIN_USER = "LOGIN_USER"
    }
    var prefs: SharedPreferences
    var editor: SharedPreferences.Editor
    var ctx: Context? = null

    init{
        this.ctx = ctx
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    fun setLoggedIn(loggedin: Boolean) {
        editor.putBoolean("loggedInMode", loggedin)
        editor.commit()
    }

    fun loggenIn(): Boolean {
        return prefs.getBoolean("loggedInMode", false)
    }

    fun setUserId(id: String) {
        with(prefs.edit()){
            putString(LOGIN_USER,id)
            commit()
        }
    }

    fun getUserId(): String{
        return prefs.getString(LOGIN_USER, "") ?: ""
    }
}