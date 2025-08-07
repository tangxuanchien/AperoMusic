package com.example.chientx_apero.model

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.chientx_apero.room_db.entity.User
import com.google.gson.Gson

object PreferenceManager {
    private const val PREF_NAME = "MyPreferences"
    private const val KEY_IS_CALL_API = "is_call_api"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_USER = "username"
    private const val KEY_PASSWORD = "password"
    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveLoginState(user: User) {
        val json = Gson().toJson(user)
        prefs?.edit {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USER, json)
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs?.getBoolean(KEY_IS_LOGGED_IN, false) ?: false
    }

    fun getSaveUser(): User? {
        val json = prefs?.getString(KEY_USER, null) ?: return null
        return Gson().fromJson(json, User::class.java)
    }

    fun setApiCalled() {
        prefs?.edit {
            putBoolean(KEY_IS_CALL_API, true)
        }
    }

    fun getApiCalled(): Boolean {
        return prefs?.getBoolean(KEY_IS_CALL_API, false) ?: false
    }
}