package com.example.characters.retrofit

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class UserRepository(private val loadingRequestData: LoadingRequestData) {

    suspend fun getUsers() = withContext(Dispatchers.IO) {
        loadingRequestData.getUsers()
    }

    suspend fun getDetailsUser(id: Int) = withContext(Dispatchers.IO) {
        loadingRequestData.getDetailsUser(id)
    }

}