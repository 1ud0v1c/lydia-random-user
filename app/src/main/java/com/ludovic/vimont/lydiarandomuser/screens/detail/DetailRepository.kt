package com.ludovic.vimont.lydiarandomuser.screens.detail

import android.content.Context
import com.ludovic.vimont.lydiarandomuser.database.UserDao
import com.ludovic.vimont.lydiarandomuser.database.UserLocal
import com.ludovic.vimont.lydiarandomuser.model.User

class DetailRepository(context: Context) {
    private val userDao: UserDao = UserLocal(context).userDatabase.userDao()

    suspend fun retrieveUser(userEmail: String): User {
        return userDao.get(userEmail)
    }
}