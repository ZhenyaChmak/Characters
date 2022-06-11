package com.example.characters.data.mapper

import com.example.characters.data.model.UserDetailsDTO
import com.example.characters.domain.model.UserDetails

internal fun UserDetailsDTO.toDomainModel(): UserDetails {
    return UserDetails(
        id = id,
        name = name,
        userPhoto = userPhoto,
        pageHttp = pageHttp
    )
}
