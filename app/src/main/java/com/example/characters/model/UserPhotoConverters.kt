package com.example.characters.model

import androidx.room.TypeConverter

class UserPhotoConverters {

    @TypeConverter
    fun toListOfStrings(userPhoto: String): List<String> {
        return userPhoto.split(",")
    }

    @TypeConverter
    fun fromListOfStrings(userPhoto: List<String>): String {
        return userPhoto.joinToString(",")
    }
}