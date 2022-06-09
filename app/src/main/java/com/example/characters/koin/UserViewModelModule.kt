package com.example.characters.koin

import com.example.characters.model.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val userViewModelModule = module {

    viewModelOf(::UserListViewModel)

}