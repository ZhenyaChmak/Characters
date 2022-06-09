package com.example.characters.model

import androidx.lifecycle.ViewModel
import com.example.characters.domain.model.User
import com.example.characters.domain.usecase.GetUsersLocalUseCase
import com.example.characters.domain.usecase.GetUsersRemoteUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class UserListViewModel(
    private val remoteUseCase: GetUsersRemoteUseCase,
    private val localUseCase: GetUsersLocalUseCase
) : ViewModel() {

    private val loadSharedFlow = MutableSharedFlow<LoadState>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var isLoading = false
    private var currentPage = 1

    fun onLoadMore() {
        loadSharedFlow.tryEmit(LoadState.LOAD)
    }

    fun onRefresh() {
        loadSharedFlow.tryEmit(LoadState.REFRESH)
    }

    fun getData(): Flow<List<User>> = flow<List<User>> {
        remoteUseCase()
            .fold(
                onSuccess = {

                },
                onFailure = {
                }
            )
    }
        .onStart {
            emit(remoteUseCase.invoke().getOrDefault(emptyList()))
        }

    enum class LoadState {
        LOAD, REFRESH
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}

/*fun getData(): Flow<List<User>> {
    return loadSharedFlow
        .filter { !isLoading }
        .onEach {
            if (it == LoadState.REFRESH) {
                currentPage = 1
            }
            isLoading = true
        }
        .map {
            getUsersUseCase()
                .fold(
                    onSuccess = {
                        userLocalUseCase.iasd(it)
                      //  userDao.insertUser(it)
                        currentPage++
                        userLocalUseCase(currentPage * PAGE_SIZE, 0)
                      //  userDao.getUsersQuantity(currentPage * PAGE_SIZE, 0)
                    },
                    onFailure = {
                        userLocalUseCase(currentPage * PAGE_SIZE, 0)
                      //  userDao.getUsersQuantity(currentPage * PAGE_SIZE, 0)
                    }
                )
        }
        .onEach {
            isLoading = false

        }
        .onStart {
            emit(userDao.getUsersQuantity(currentPage * PAGE_SIZE, 0))
        }
}*/





