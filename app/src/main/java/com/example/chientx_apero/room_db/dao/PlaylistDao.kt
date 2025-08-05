package com.example.chientx_apero.room_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chientx_apero.room_db.entity.Playlist

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist")
    suspend fun getAll(): List<Playlist>

    @Query("SELECT * FROM playlist WHERE id = :playlistIds")
    suspend fun getPlaylistById(playlistIds: Long): Playlist

    @Query("SELECT * FROM playlist WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): Playlist

    @Insert
    suspend fun insertAll(vararg playlist: Playlist)

    @Query("DELETE FROM playlist WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Query("UPDATE playlist SET name = :name WHERE id = :id")
    suspend fun updateNameById(name: String, id: Int): Int

    @Query("UPDATE playlist SET totalSongs = :totalSongs WHERE id = :id")
    suspend fun updateTotalSongsById(totalSongs: Int, id: Int): Int


}