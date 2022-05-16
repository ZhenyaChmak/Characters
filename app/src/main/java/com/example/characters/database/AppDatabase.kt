package com.example.characters.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.characters.model.User
import com.example.characters.model.UserPhotoConverters

@Database(entities = [User::class], version = 1)
@TypeConverters(UserPhotoConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}
