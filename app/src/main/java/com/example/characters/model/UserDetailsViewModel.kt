package com.example.characters.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characters.domain.usecase.GetUserDetailsUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class UserDetailsViewModel(
    private val detailsUseCase: GetUserDetailsUseCase,
    private val userId: Int
) : ViewModel() {

    /*private val loadMoreFlow = MutableSharedFlow<Int>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )*/

    val dataFlow = flow{
        emit(detailsUseCase(userId))
    }.shareIn(
        scope = viewModelScope,
        replay = 1,
        started = SharingStarted.Eagerly
    )
    /*val dataFlow = loadMoreFlow
        .map { value ->
            detailsUseCase(value)
        }.shareIn(
            scope = viewModelScope,
            replay = 1,
            started = SharingStarted.Eagerly
        )*/

/*
    fun onLoadMoreDetails(userId: Int) {
        loadMoreFlow.tryEmit(userId)

    }
*/

}