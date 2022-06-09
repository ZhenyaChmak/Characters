package com.example.characters.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.characters.data.model.UserEntity

@Dao
internal interface UserDao {

    @Query("SELECT * FROM userentity")
    suspend fun getUsers(): List<UserEntity>

    @Query("SELECT * FROM userentity LIMIT :limit OFFSET :offset")
    suspend fun getUsersQuantity(limit: Int, offset: Int): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: List<UserEntity>)

}