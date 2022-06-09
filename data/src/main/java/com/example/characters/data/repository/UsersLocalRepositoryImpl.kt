package com.example.characters.data.repository

import com.example.characters.data.database.UserDao
import com.example.characters.data.mapper.toDomainModel
import com.example.characters.data.mapper.toUserEntity
import com.example.characters.domain.model.User
import com.example.characters.domain.repository.UsersLocalRepository

internal class UsersLocalRepositoryImpl(
    private val userDao: UserDao
) : UsersLocalRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return runCatching {
            userDao.getUsers()
        }.map {
            it.toDomainModel()
        }
    }

    override suspend fun getUsersQuantity(limit: Int, offset: Int): Result<List<User>> {
        return runCatching {
            userDao.getUsersQuantity(limit, offset)
        }.map {
            it.toDomainModel()
        }
    }

    override suspend fun insertUser(user: List<User>) {
        runCatching {
            userDao.insertUser(user.map {
                it.toUserEntity()
            })
        }
    }

}