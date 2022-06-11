package com.example.characters.domain.model

data class UserDetails(
    val id: Int,
    val name: String,
    val userPhoto: List<String>,
    val pageHttp: String,
)