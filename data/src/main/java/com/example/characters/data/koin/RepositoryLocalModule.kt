package com.example.characters.data.koin

import com.example.characters.data.repository.UsersLocalRepositoryImpl
import com.example.characters.domain.repository.UsersLocalRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryLocalModule = module {

    singleOf (::UsersLocalRepositoryImpl){
        bind<UsersLocalRepository>()
    }

}