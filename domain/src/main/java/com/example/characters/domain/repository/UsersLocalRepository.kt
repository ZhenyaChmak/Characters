package com.example.characters.domain.repository

import com.example.characters.domain.model.User

interface UsersLocalRepository {

    suspend fun getUsers(): Result<List<User>>

    suspend fun getUsersQuantity(limit: Int, offset: Int): Result<List<User>>

    suspend fun insertUser(user: List<User>)

}