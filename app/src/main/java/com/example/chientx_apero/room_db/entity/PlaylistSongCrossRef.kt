package com.example.chientx_apero.room_db.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["playlistId", "songId"],
    foreignKeys = [
        ForeignKey(
            entity = Playlist::class,
            parentColumns = ["id"],
            childColumns = ["playlistId"]
        ),
        ForeignKey(entity = Song::class, parentColumns = ["id"], childColumns = ["songId"])
    ]
)
data class PlaylistSongCrossRef(
    val playlistId: Int,
    val songId: Int,
)