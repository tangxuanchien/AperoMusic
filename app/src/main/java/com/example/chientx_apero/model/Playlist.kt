package com.example.chientx_apero.model

import android.graphics.Bitmap

data class Playlist(
    val id: Int,
    var name: String,
    val image: Int,
    var song: MutableList<Song> = mutableListOf<Song>()
)