package com.example.chientx_apero.model

import android.graphics.Bitmap

data class SongModel(
    val id: Long,
    val name: String,
    val artist: String,
    val data: String,
    val duration: String,
    val image: Bitmap
)