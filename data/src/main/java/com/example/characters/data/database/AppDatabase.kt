package com.example.characters.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.characters.data.model.UserEntity
import com.example.characters.data.model.UserPhotoConverters

@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(UserPhotoConverters::class)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}
