package com.example.characters.domain.repository

import com.example.characters.domain.model.UserDetails

interface UserDetailsRepository {

    suspend fun getUserDetails(id: Int): Result<UserDetails>

}