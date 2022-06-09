package com.example.characters.data.model

import androidx.room.TypeConverter

internal class UserPhotoConverters {

    @TypeConverter
    fun toListOfStrings(userPhoto: String): List<String> {
        return userPhoto.split(",")
    }

    @TypeConverter
    fun fromListOfStrings(userPhoto: List<String>): String {
        return userPhoto.joinToString(",")
    }
}