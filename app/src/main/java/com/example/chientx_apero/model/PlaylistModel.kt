package com.example.chientx_apero.model

data class PlaylistModel(
    val id: Int,
    var name: String,
    val image: Int,
    var songModels: MutableList<SongModel> = mutableListOf<SongModel>()
)