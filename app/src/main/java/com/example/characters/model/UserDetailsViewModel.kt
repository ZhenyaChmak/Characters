package com.example.characters.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characters.retrofit.UserRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class UserDetailsViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val loadMoreFlow = MutableSharedFlow<Int>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val dataFlow = loadMoreFlow
        .map { value ->
            repository.getDetailsUser(value)
        }.shareIn(
            scope = viewModelScope,
            replay = 1,
            started = SharingStarted.Eagerly
        )

    fun onLoadMoreDetails(userId: Int) {
        loadMoreFlow.tryEmit(userId)
    }

}