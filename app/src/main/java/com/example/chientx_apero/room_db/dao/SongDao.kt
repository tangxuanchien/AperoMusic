package com.example.chientx_apero.room_db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Query("""
            SELECT * FROM song WHERE library = 'local'
            """)
    suspend fun getAllFromLocal(): List<Song>
    @Query("""
            SELECT * FROM song WHERE library = 'remote'
            """)
    suspend fun getAllFromRemote(): List<Song>
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