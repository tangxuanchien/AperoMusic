package com.example.chientx_apero.room_db.converter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.core.net.toUri
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class SongEntityTypeConverter {
    @TypeConverter
    fun fromSongDataPath(value: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        value.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }

    @TypeConverter
    fun toSongDataPath(value: String): Bitmap {
        val bytes = Base64.decode(value, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}