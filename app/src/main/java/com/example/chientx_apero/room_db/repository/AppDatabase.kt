package com.example.chientx_apero.room_db.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.chientx_apero.room_db.dao.PlaylistDao
import com.example.chientx_apero.room_db.dao.SongDao
import com.example.chientx_apero.room_db.dao.UserDao
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.room_db.entity.User

@Database(entities = [User::class, Playlist::class, Song::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun songDao(): SongDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}