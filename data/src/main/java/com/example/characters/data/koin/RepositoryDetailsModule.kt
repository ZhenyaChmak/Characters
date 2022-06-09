package com.example.characters.data.koin

import com.example.characters.data.repository.UserDetailsRepositoryImpl
import com.example.characters.domain.repository.UserDetailsRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryDetailsModule = module {

    singleOf(::UserDetailsRepositoryImpl) {
        bind<UserDetailsRepository>()
    }

}