package com.example.chientx_apero.room_db.dao

import android.net.Uri
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.chientx_apero.room_db.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    suspend fun checkExistAccount(username: String): User?

    @Query("SELECT * FROM user WHERE username = :username and password = :password LIMIT 1")
    suspend fun checkPermission(username: String, password: String): User?

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun findByUsername(username: String): User

    @Query("UPDATE user SET name = :name, phone = :phone, university = :university, describe = :describe WHERE username = :username")
    suspend fun updateInformation(name: String, phone: String, university: String, describe: String, username: String)

    @Query("UPDATE user SET avatar = :avatar WHERE username = :username")
    suspend fun updateAvatar(avatar: Uri, username: String)

    @Insert
    suspend fun insertAll(vararg users: User)

    @Delete
    suspend fun delete(user: User)
}