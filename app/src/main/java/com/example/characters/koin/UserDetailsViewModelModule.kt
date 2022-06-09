package com.example.characters.koin

import com.example.characters.model.UserDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val userDetailsViewModelModule = module {

    viewModelOf(::UserDetailsViewModel)

}