package com.example.chientx_apero.room_db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.chientx_apero.room_db.entity.Song

@Dao
interface SongDao {
    @Query("SELECT * FROM song")
    fun getAll(): List<Song>

    @Query("SELECT * FROM song WHERE id IN (:songIds)")
    fun loadAllByIds(songIds: IntArray): List<Song>

    @Query("SELECT * FROM song WHERE name = :name LIMIT 1")
    fun findByName(name: String): Song

    @Insert
    fun insertAll(vararg users: Song)

    @Delete
    fun delete(user: Song)
}