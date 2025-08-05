package com.example.chientx_apero.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chientx_apero.room_db.converter.SongEntityTypeConverter
import com.example.chientx_apero.room_db.converter.UserEntityTypeConverter
import com.example.chientx_apero.room_db.dao.PlaylistDao
import com.example.chientx_apero.room_db.dao.PlaylistSongCrossRefDao
import com.example.chientx_apero.room_db.dao.SongDao
import com.example.chientx_apero.room_db.dao.UserDao
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.PlaylistSongCrossRef
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.room_db.entity.User

@Database(entities = [User::class, Playlist::class, Song::class, PlaylistSongCrossRef::class], version = 1)
@TypeConverters(UserEntityTypeConverter::class, SongEntityTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun songDao(): SongDao
    abstract fun playlistSongCrossRefDao(): PlaylistSongCrossRefDao

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