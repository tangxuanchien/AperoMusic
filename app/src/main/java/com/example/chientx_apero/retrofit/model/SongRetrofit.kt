package com.example.chientx_apero.retrofit.model

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.util.Size
import androidx.core.net.toUri
import com.example.chientx_apero.R
import com.example.chientx_apero.room_db.entity.Song

data class SongRetrofit(
    val title: String,
    val artist: String,
    val kind: String,
    val duration: Int,
    var path: String,
)

fun SongRetrofit.toSong(context: Context): Song {
    val totalSeconds = duration / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    val durationFormat = String.format("%02d:%02d", minutes, seconds)

    return Song(
        id = 0L,
        name = this.title,
        artist = this.artist,
        data = this.path.toUri(),
        duration = durationFormat,
        image = null,
        library = "remote"
    )
}
