package com.example.chientx_apero.room_db.repository

import android.content.Context
import com.example.chientx_apero.room_db.AppDatabase
import com.example.chientx_apero.room_db.dao.SongDao
import com.example.chientx_apero.room_db.entity.Song
import kotlinx.coroutines.flow.Flow

class SongRepository(context: Context) {
    val songDao = AppDatabase.getDatabase(context).songDao()

    suspend fun getAllSongsFromRemote(): List<Song> = songDao.getAllFromRemote()
    suspend fun getAllSongsFromLocal(): List<Song> = songDao.getAllFromLocal()
    suspend fun insertSongs(songs: List<Song>) = songDao.insertAll(songs)
    suspend fun getAllSongsInPlaylist(playlistId: Long) = songDao.getAllSongsInPlaylist(playlistId)
    suspend fun getSongById(id: Long) = songDao.getSongById(id)

}