package com.example.chientx_apero.room_db.entity

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val data: Uri,
    val image: Bitmap,
    val artist: String,
    val duration: String,
    val library: String
)