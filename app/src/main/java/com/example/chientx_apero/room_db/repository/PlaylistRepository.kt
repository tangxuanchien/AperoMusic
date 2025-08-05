package com.example.chientx_apero.room_db.repository

import android.content.Context
import com.example.chientx_apero.room_db.AppDatabase
import com.example.chientx_apero.room_db.entity.Playlist

class PlaylistRepository(context: Context) {
    val playlistDao = AppDatabase.getDatabase(context).playlistDao()

    suspend fun getAllPlaylists(): List<Playlist> = playlistDao.getAll()
    suspend fun insertPlaylist(playlist: Playlist) = playlistDao.insertAll(playlist)

    suspend fun deletePlaylist(idPlaylist: Int) = playlistDao.deleteById(idPlaylist)
    suspend fun updatePlaylistName(name: String, id: Int) = playlistDao.updateNameById(name, id)
}