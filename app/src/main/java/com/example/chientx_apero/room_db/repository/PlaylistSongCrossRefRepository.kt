package com.example.chientx_apero.room_db.repository

import android.content.Context
import com.example.chientx_apero.room_db.AppDatabase
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.Song


class PlaylistSongCrossRefRepository(context: Context) {
    val playlistSongCrossRefDao = AppDatabase.getDatabase(context).playlistSongCrossRefDao()

    suspend fun addSongToPlaylist(playlistId: Long, songId: Long) = playlistSongCrossRefDao.insert(playlistId, songId)
    suspend fun getTotalSongsInPlaylistById(playlistId: Long) = playlistSongCrossRefDao.getTotalSongsInPlaylistById(playlistId)
    suspend fun deleteBySongId(songId: Long) = playlistSongCrossRefDao.deleteBySongId(songId)
    suspend fun deleteByPlaylistId(playlistId: Long) = playlistSongCrossRefDao.deleteByPlaylistId(playlistId)
    suspend fun hasSongInPlaylist(playlistId: Long, songId: Long) = playlistSongCrossRefDao.hasSongInPlaylist(playlistId, songId)
    suspend fun deleteSongInPlaylistId(playlistId: Long, songId: Long) = playlistSongCrossRefDao.deleteSongInPlaylistId(playlistId, songId)
}