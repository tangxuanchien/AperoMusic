package com.example.chientx_apero.room_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.PlaylistSongCrossRef
import com.example.chientx_apero.room_db.entity.Song

@Dao
interface PlaylistSongCrossRefDao {
    @Query("INSERT INTO playlistsongcrossref VALUES (:playlistId, :songId)")
    suspend fun insert(playlistId: Long, songId: Long)

    @Query("SELECT count(*) FROM playlistsongcrossref WHERE playlistId = :playlistId")
    suspend fun getTotalSongsInPlaylistById(playlistId: Long) : Long

    @Query("DELETE FROM playlistsongcrossref WHERE songId = :songId")
    suspend fun deleteBySongId(songId: Long): Int

    @Query("DELETE FROM playlistsongcrossref WHERE playlistId = :playlistId")
    suspend fun deleteByPlaylistId(playlistId: Long): Int

    @Query("DELETE FROM playlistsongcrossref WHERE playlistId = :playlistId and songId = :songId")
    suspend fun deleteSongInPlaylistId(playlistId: Long, songId: Long): Int
    @Query("SELECT * FROM playlistsongcrossref WHERE songId = :songId AND playlistId = :playlistId")
    suspend fun hasSongInPlaylist(playlistId: Long, songId: Long): PlaylistSongCrossRef?
}