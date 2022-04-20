package com.example.characters.model

import com.google.gson.annotations.SerializedName

data class User(
    val id : Int,
    val name : String,
    @SerializedName("images")
    val userPhoto : List<String>,
    val about: List<String>
)