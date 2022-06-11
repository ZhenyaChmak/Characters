package com.example.characters.data.repository

import com.example.characters.data.api.GithubApi
import com.example.characters.data.mapper.toDomainModel
import com.example.characters.domain.model.User
import com.example.characters.domain.repository.UsersRemoteRepository

internal class UsersRemoteRepositoryImpl(
    private val githubApi: GithubApi
) : UsersRemoteRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return runCatching {
            githubApi.getUsers()
        }.map {
            it.toDomainModel()
        }
    }

}