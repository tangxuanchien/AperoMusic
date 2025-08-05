package com.example.chientx_apero.room_db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Playlist(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val image: String?,
    val totalSongs: Int
)