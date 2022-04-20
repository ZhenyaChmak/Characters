package com.example.characters.model

import com.google.gson.annotations.SerializedName

sealed class UserLoading {

    data class User(
        val id: Int,
        val name: String,
        @SerializedName("images")
        val userPhoto: List<String>,
    ) : UserLoading()

    object Loading : UserLoading()
}