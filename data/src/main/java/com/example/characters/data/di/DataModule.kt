package com.example.characters.data.di

import org.koin.dsl.module

val dataModule = module {

    includes(
        databaseModule,
        networkModule,
        repositoryDetailsModule,
        repositoryLocalModule,
        repositoryRemoteModule,
        useCaseDetailsModule,
        useCaseLocalModule,
        useCaseRemoteModule
    )

}