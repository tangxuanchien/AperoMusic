package com.example.chientx_apero.ui.playlist.components

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Size
import androidx.core.net.toUri
import com.example.chientx_apero.room_db.entity.Song

@SuppressLint("DefaultLocale")
fun getAllMp3Files(context: Context): MutableList<Song> {
    val songs = mutableListOf<Song>()
    val contentResolver: ContentResolver = context.contentResolver
    val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val selection = "${MediaStore.Audio.Media.DURATION} > 0"
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.DURATION
    )
    val cursor = contentResolver.query(uri, projection, selection, null, null)
    cursor?.use {
        val idColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val titleColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        val artistColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        val dataColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        val durationColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

        while (it.moveToNext()) {
            val id = it.getLong(idColumn)
            val title = it.getString(titleColumn)
            val artist = it.getString(artistColumn)
            val data = it.getString(dataColumn)
            val duration = it.getLong(durationColumn)
            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                id
            )
            val imageCover = context.contentResolver.loadThumbnail(
                contentUri,
                Size(300, 300),
                null
            )

            val totalSeconds = duration / 1000
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60
            val durationFormat = String.format("%02d:%02d", minutes, seconds)

            songs.add(Song(id, title, data.toUri(), imageCover, artist, durationFormat))
        }
    }
    return songs
}