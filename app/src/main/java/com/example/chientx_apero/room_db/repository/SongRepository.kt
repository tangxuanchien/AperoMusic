package com.example.chientx_apero.room_db.repository

import android.content.Context
import com.example.chientx_apero.room_db.AppDatabase
import com.example.chientx_apero.room_db.dao.SongDao
import com.example.chientx_apero.room_db.entity.Song

class SongRepository(context: Context) {
    val songDao = AppDatabase.getDatabase(context).songDao()

    suspend fun getAllSongs(): List<Song> = songDao.getAll()
    suspend fun insertSongs(songs: List<Song>) = songDao.insertAll(songs)
}