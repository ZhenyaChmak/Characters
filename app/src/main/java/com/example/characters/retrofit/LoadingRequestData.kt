package com.example.characters.retrofit

import com.example.characters.model.UserDetails
import com.example.characters.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface LoadingRequestData {

    @GET("api/v1/characters")
    suspend fun getUsers(): List<User>

    @GET("api/v1/characters/{id}")
    suspend fun getDetailsUser(
        @Path("id") id: Int
    ): UserDetails

}