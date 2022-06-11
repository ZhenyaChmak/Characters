package com.example.characters.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characters.domain.model.LoadState
import com.example.characters.domain.model.User
import com.example.characters.domain.usecase.GetUsersLocalUseCase
import com.example.characters.domain.usecase.GetUsersRemoteUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserListViewModel(
    private val remoteUseCase: GetUsersRemoteUseCase,
    private val localUseCase: GetUsersLocalUseCase
) : ViewModel() {

    private val loadSharedFlow = MutableSharedFlow<LoadState>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _nextDetails = MutableSharedFlow<User>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val nextDetails = _nextDetails.asSharedFlow()

    private var isLoading = false
    private var currentPage = 1

    fun getNextDetails(user: User) = viewModelScope
        .launch {
            _nextDetails.tryEmit(user)
        }

    fun onLoadMore() {
        loadSharedFlow.tryEmit(LoadState.LOAD)
    }

    fun onRefresh(): Boolean {
        return loadSharedFlow.tryEmit(LoadState.REFRESH)
    }

    val getData =
        loadSharedFlow
            .onEach {
                if (it == LoadState.REFRESH) {
                    currentPage = 0
                    isLoading = true
                }
                if (it == LoadState.LOAD)
                    isLoading = true
            }
            .filter { isLoading }
            .map {
                remoteUseCase()
                    .fold(
                        onSuccess = { list ->
                            localUseCase(list)
                            currentPage++
                            localUseCase(currentPage * PAGE_SIZE, 0)
                        },
                        onFailure = {
                            emptyList()
                        }
                    )
            }
            .onEach {
                loadSharedFlow.tryEmit(LoadState.NOT_LOAD)
                isLoading = false
            }
            .onStart {
                if ((localUseCase(currentPage * PAGE_SIZE, 0)).isEmpty()) {
                    remoteUseCase()
                        .fold(onSuccess = {
                            localUseCase(it)
                            emit(localUseCase(currentPage * PAGE_SIZE, 0))
                            it
                        },
                            onFailure = {
                                emit(emptyList())
                            })
                } else {
                    emit(localUseCase(currentPage * PAGE_SIZE, 0))
                }
            }

    companion object {
        private const val PAGE_SIZE = 10
    }
}




