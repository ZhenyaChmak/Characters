package com.example.characters.data.repository

import com.example.characters.data.api.GithubApi
import com.example.characters.data.mapper.toDomainModel
import com.example.characters.domain.model.UserDetails
import com.example.characters.domain.repository.UserDetailsRepository

internal class UserDetailsRepositoryImpl(
    private val githubApi: GithubApi
) : UserDetailsRepository {

    override suspend fun getUserDetails(id: Int): Result<UserDetails> {
        return runCatching {
            githubApi.getDetailsUser(id)
        }.map {
            it.toDomainModel()
        }
    }

}