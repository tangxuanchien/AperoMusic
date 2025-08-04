package com.example.chientx_apero.room_db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.chientx_apero.room_db.entity.Playlist

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist")
    fun getAll(): List<Playlist>

    @Query("SELECT * FROM playlist WHERE id IN (:playlistIds)")
    fun loadAllByIds(playlistIds: IntArray): List<Playlist>

    @Query("SELECT * FROM playlist WHERE name = :name LIMIT 1")
    fun findByName(name: String): Playlist

    @Insert
    fun insertAll(vararg playlist: Playlist)

    @Delete
    fun delete(playlist: Playlist)
}