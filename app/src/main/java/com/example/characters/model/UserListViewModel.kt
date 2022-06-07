package com.example.characters.model

import androidx.lifecycle.ViewModel
import com.example.characters.database.UserDao
import com.example.characters.retrofit.UserRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class UserListViewModel(
    private val repository: UserRepository,
    private val userDao: UserDao
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

    fun getData(): Flow<List<User>> {
        return loadSharedFlow
            .filter { !isLoading }
            .onEach {
                if (it == LoadState.REFRESH) {
                    currentPage = 1
                }
                isLoading = true
            }
            .map {
                repository.getUsers()
                    .fold(
                        onSuccess = {
                            userDao.insertUser(it)
                            currentPage++
                            userDao.getUsersQuantity(currentPage * PAGE_SIZE, 0)
                        },
                        onFailure = {
                            userDao.getUsersQuantity(currentPage * PAGE_SIZE, 0)
                        }
                    )
            }
            .onEach {
                isLoading = false

            }
            .onStart {
                emit(userDao.getUsersQuantity(currentPage * PAGE_SIZE, 0))
            }
    }

    enum class LoadState {
        LOAD, REFRESH
    }

    companion object {
        private const val PAGE_SIZE = 10
    }

}