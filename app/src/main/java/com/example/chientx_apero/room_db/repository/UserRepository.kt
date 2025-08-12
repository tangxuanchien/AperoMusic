package com.example.chientx_apero.room_db.repository

import android.content.Context
import com.example.chientx_apero.room_db.AppDatabase
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.User


class UserRepository(context: Context) {
    val userDao = AppDatabase.getDatabase(context).userDao()

    suspend fun checkExistAccount(userId: Long): User? = userDao.getUserByIds(userId)
    suspend fun getUserByIds(userId: Long): User? = userDao.getUserByIds(userId)
}