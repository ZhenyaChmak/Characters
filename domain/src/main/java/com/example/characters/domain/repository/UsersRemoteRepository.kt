package com.example.characters.domain.repository

import com.example.characters.domain.model.User

interface UsersRemoteRepository {

    suspend fun getUsers(): Result<List<User>>

}