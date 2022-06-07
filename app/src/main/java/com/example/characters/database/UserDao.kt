package com.example.characters.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.characters.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM user LIMIT :limit OFFSET :offset")
    suspend fun getUsersQuantity(limit:Int, offset:Int): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: List<User>)

}