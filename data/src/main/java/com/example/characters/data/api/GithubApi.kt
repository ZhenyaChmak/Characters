package com.example.characters.data.api

import com.example.characters.data.model.UserDTO
import com.example.characters.data.model.UserDetailsDTO
import retrofit2.http.GET
import retrofit2.http.Path

internal interface GithubApi {

    @GET("api/v1/characters")
    suspend fun getUsers(): List<UserDTO>

    @GET("api/v1/characters/{id}")
    suspend fun getDetailsUser(
        @Path("id") id: Int
    ): UserDetailsDTO

}