package com.example.characters.data.koin

import com.example.characters.domain.usecase.GetUsersRemoteUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseRemoteModule = module {

    singleOf(::GetUsersRemoteUseCase)

}
