package com.example.characters.retrofit

import com.example.characters.model.UserDetails
import com.example.characters.model.PageItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LoadingRequestData {

    @GET("api/v1/characters")
    fun getUsers(

    ): Call<List<PageItem.User>>

    @GET("api/v1/characters/{id}")
    fun getDetailsUser(
        @Path("id") id: Int
    ): Call<UserDetails>

}