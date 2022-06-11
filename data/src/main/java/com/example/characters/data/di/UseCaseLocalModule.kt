package com.example.characters.data.di

import com.example.characters.domain.usecase.GetUsersLocalUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseLocalModule = module {

    singleOf(::GetUsersLocalUseCase)

}