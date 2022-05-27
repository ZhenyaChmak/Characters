package com.example.characters.koin

import com.example.characters.model.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userViewModelModule = module {
    viewModel {
        UserListViewModel(get(), get())
    }
}