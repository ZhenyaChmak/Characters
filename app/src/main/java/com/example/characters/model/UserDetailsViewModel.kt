package com.example.characters.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characters.domain.usecase.GetUserDetailsUseCase
import kotlinx.coroutines.flow.*

class UserDetailsViewModel(
    private val detailsUseCase: GetUserDetailsUseCase,
    private val userId: Int
) : ViewModel() {

    val dataFlow = flow {
        emit(detailsUseCase(userId))
    }.shareIn(
        scope = viewModelScope,
        replay = 1,
        started = SharingStarted.Eagerly
    )

}