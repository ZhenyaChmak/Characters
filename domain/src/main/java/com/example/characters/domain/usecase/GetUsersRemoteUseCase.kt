package com.example.characters.domain.usecase

import com.example.characters.domain.model.User
import com.example.characters.domain.repository.UsersRemoteRepository

class GetUsersRemoteUseCase(
    private val usersRemoteRepository: UsersRemoteRepository
) {

    suspend operator fun invoke(): Result<List<User>> {
        return usersRemoteRepository.getUsers()
    }

}