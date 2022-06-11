package com.example.characters.data.model

import com.google.gson.annotations.SerializedName

internal data class UserDTO(
    val id: Int,
    val name: String,
    @SerializedName("images")
    val userPhoto: List<String>
)