package com.example.chientx_apero.retrofit.model

import com.google.gson.annotations.SerializedName

data class ArtistRetrofit(
    val name: String,
    val playcount: String,
    val listeners: String,
    val mbid: String,
    val url: String,
    val streamable: String,
    val image: List<ArtistImage>
)

data class ArtistImage(
    @SerializedName("#text")
    val url: String,
    val size: String
)

data class TopArtistsResponse(
    val artists: ArtistsWrapper
)

data class ArtistsWrapper(
    val artist: List<ArtistRetrofit>
)