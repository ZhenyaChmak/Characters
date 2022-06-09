package com.example.characters.domain.usecase

import com.example.characters.domain.model.User
import com.example.characters.domain.repository.UsersLocalRepository

class GetUsersLocalUseCase(
    private val usersLocalUseCase: UsersLocalRepository
) {

    suspend operator fun invoke(): Result<List<User>> {
        return usersLocalUseCase.getUsers()
    }

    suspend operator fun invoke(limit: Int, offset: Int): Result<List<User>> {
        return usersLocalUseCase.getUsersQuantity(limit, offset)
    }

    suspend operator fun invoke(user: List<User>) {
        usersLocalUseCase.insertUser(user)
    }

}