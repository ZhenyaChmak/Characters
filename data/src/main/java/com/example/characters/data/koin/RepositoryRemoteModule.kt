package com.example.characters.data.koin

import com.example.characters.data.repository.UsersRemoteRepositoryImpl
import com.example.characters.domain.repository.UsersRemoteRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryRemoteModule = module {

    singleOf(::UsersRemoteRepositoryImpl) {
        bind<UsersRemoteRepository>()
    }

}
