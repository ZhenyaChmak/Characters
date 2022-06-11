package com.example.characters.domain.repository

import com.example.characters.domain.model.User

interface UsersLocalRepository {

    suspend fun getUsers(): List<User>

    suspend fun getUsersQuantity(limit: Int, offset: Int): List<User>

    suspend fun insertUser(user: List<User>)

}