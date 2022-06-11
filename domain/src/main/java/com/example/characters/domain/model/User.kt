package com.example.characters.domain.model

data class User(
    val id: Int,
    val name: String,
    val userPhoto: List<String>
)