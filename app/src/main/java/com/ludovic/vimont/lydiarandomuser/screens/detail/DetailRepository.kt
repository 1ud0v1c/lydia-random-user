package com.ludovic.vimont.lydiarandomuser.screens.detail

import com.ludovic.vimont.lydiarandomuser.database.UserDao
import com.ludovic.vimont.lydiarandomuser.model.User
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetailRepository: KoinComponent {
    private val userDao: UserDao by inject()

    suspend fun retrieveUser(userEmail: String): User {
        return userDao.get(userEmail)
    }
}