package com.example.chientx_apero.room_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.Song

@Dao
interface PlaylistSongCrossRefDao {
    @Query("INSERT INTO playlistsongcrossref VALUES (:playlistId, :songId)")
    suspend fun insert(playlistId: Long, songId: Long)

}