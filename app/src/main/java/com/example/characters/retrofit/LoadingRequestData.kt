package com.example.characters.retrofit

import com.example.characters.model.User
import com.example.characters.model.UserDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LoadingRequestData {

    @GET("api/v1/characters")
    fun getUsers(): Call<List<User>>

    @GET("api/v1/characters/{id}")
    fun getDetailsUser(
        @Path("id") id: Long
    ): Call<UserDetails>

}