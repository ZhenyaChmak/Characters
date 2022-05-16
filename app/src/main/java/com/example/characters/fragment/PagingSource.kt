package com.example.characters.fragment

import com.example.characters.database.UserDao
import com.example.characters.model.User
import com.example.characters.retrofit.UserRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class PagingSource(
    private val userRepository: UserRepository,
    private val userDao: UserDao
) {

    private val loadSharedFlow = MutableSharedFlow<LoadState>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var isLoading = false
    private var currentPage = 0

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
                /*if (it == LoadState.REFRESH)
                    currentPage = 0*/
                isLoading = true
            }
            .map {
                userRepository.getUsers()
            }
            .onEach {
                userDao.insertUser(it)
                isLoading = false
                //  currentPage++
            }
            .runningReduce { accumulator, value -> accumulator + value }
            .onStart {
                emit(userDao.getUsers())
            }
    }

    enum class LoadState {
        LOAD, REFRESH
    }
}