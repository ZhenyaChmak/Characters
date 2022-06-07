package com.example.characters.koin

import com.example.characters.retrofit.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        UserRepository(get())
    }
}