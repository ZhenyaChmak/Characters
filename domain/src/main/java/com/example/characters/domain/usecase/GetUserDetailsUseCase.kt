package com.example.characters.domain.usecase

import com.example.characters.domain.model.UserDetails
import com.example.characters.domain.repository.UserDetailsRepository

class GetUserDetailsUseCase(
    private val userDetailsRepository: UserDetailsRepository
) {

    suspend operator fun invoke(userId: Int): Result<UserDetails> {
        return userDetailsRepository.getUserDetails(userId)
    }

}

