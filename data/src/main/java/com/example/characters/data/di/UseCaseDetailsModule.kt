package com.example.characters.data.di

import com.example.characters.domain.usecase.GetUserDetailsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val useCaseDetailsModule = module {

    singleOf(::GetUserDetailsUseCase)

}