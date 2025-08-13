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
    private const val KEY_USER = "userId"
    private const val KEY_PASSWORD = "password"
    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun clearLoginState() {
        prefs?.edit {
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_USER)
            remove(KEY_PASSWORD)
        }
    }

    fun saveLoginState(userId: Long) {
        prefs?.edit {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putLong(KEY_USER, userId)
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs?.getBoolean(KEY_IS_LOGGED_IN, false) ?: false
    }

    fun getSaveUserId(): Long? {
        return prefs?.getLong(KEY_USER, 0L) ?: 0L
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