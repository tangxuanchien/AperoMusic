package com.example.chientx_apero.room_db.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String?,
    val describe: String?,
    val avatar: Uri?,
    val university: String?,
    val phone: String?,
    val password: String?,
    val email: String?,
    val username: String?,
)