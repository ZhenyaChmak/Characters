package com.example.characters.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ServiceLocator {

    private val retrofitService by lazy {
        retrofit.create<LoadingRequestData>()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://naruto-api.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideHttpClient())
        .build()

    private fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    fun provideRepository(): UserRepository {
        return UserRepository(retrofitService)
    }

}