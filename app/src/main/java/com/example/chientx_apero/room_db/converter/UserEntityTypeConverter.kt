package com.example.chientx_apero.room_db.converter

import android.net.Uri
import androidx.core.net.toUri
import androidx.room.TypeConverter

class UserEntityTypeConverter {
    @TypeConverter
    fun fromAvatarPath(value: Uri?): String?{
        return value?.toString()
    }

    @TypeConverter
    fun toAvatarPath(value: String?): Uri?{
        return value?.toUri()
    }
}