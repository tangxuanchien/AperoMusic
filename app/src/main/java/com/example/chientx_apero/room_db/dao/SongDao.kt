package com.example.chientx_apero.room_db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.Song

@Dao
interface SongDao {
    @Query("SELECT * FROM song")
    suspend fun getAll(): List<Song>
    @Query("SELECT * FROM song WHERE id IN (:songIds)")
    suspend fun loadAllByIds(songIds: IntArray): List<Song>
    @Query("SELECT * FROM song WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): Song
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(songs: List<Song>)
    @Delete
    suspend fun delete(user: Song)
    @Query(
        """
    SELECT s.* FROM Song s
    INNER JOIN PlaylistSongCrossRef psc ON s.id = psc.songId
    WHERE psc.playlistId = :playlistId
    """
    )
    suspend fun getAllSongsInPlaylist(playlistId: Long): List<Song>
}