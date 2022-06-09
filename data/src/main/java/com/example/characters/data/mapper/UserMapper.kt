package com.example.characters.data.mapper

import com.example.characters.data.model.UserDTO
import com.example.characters.domain.model.User

internal fun List<UserDTO>.toDomainModel(): List<User> {
    return map {
        it.toDomainModel()
    }
}

internal fun UserDTO.toDomainModel(): User {
    return User(
        id = id,
        name = name,
        userPhoto = userPhoto
    )
}

