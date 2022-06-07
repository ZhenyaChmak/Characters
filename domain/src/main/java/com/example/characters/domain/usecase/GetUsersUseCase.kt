package com.example.characters.domain.usecase

import com.example.characters.domain.model.User
import com.example.characters.domain.repository.UserRepository

class GetUsersUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Result<List<User>> {
        return userRepository.getUsers()
    }
}