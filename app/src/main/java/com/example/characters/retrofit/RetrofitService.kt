package com.example.characters.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitService {

    private val retrofitService by lazy { retrofit.create<LoadingRequestData>() }

    private val retrofit  = Retrofit.Builder()
        .baseUrl("https://naruto-api.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /*fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.MINUTES)
            .connectTimeout(15, TimeUnit.MINUTES)
            .build()
    }*/

    fun loadingRetrofitService() : LoadingRequestData{
        return retrofitService
    }
}