package com.example.characters.domain.usecase

import com.example.characters.domain.model.UserDetails
import com.example.characters.domain.repository.UserRepository

class GetUserDetailsUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(id: Int): Result<UserDetails> {
        return userRepository.getUserDetails(id)
    }
}

