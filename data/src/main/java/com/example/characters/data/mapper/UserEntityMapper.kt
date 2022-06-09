package com.example.characters.data.mapper

import com.example.characters.data.model.UserEntity
import com.example.characters.domain.model.User

internal fun List<UserEntity>.toDomainModel(): List<User> {
    return map {
        it.toDomainModel()
    }
}

internal fun UserEntity.toDomainModel(): User {
    return User(
        id = id,
        name = name,
        userPhoto = userPhoto
    )
}

internal fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        userPhoto = userPhoto
    )
}
