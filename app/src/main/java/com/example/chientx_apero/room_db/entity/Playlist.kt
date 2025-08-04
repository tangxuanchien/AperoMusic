package com.example.chientx_apero.room_db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Playlist(
    @PrimaryKey val id: Int,
    val name: String
)