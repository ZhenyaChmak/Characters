package com.example.characters.domain.repository

import com.example.characters.domain.model.User
import com.example.characters.domain.model.UserDetails

interface UserRepository {

    suspend fun getUsers(): Result<List<User>>

    suspend fun getUserDetails(id: Int): Result<UserDetails>
}