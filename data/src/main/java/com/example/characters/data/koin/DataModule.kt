package com.example.characters.data.koin

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