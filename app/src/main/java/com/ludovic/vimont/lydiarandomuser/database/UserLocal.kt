package com.ludovic.vimont.lydiarandomuser.database

import android.content.Context
import androidx.room.Room

class UserLocal(context: Context) {
    val userDatabase: UserDatabase

    init {
        val databaseName: String = UserDatabase.DATABASE_NAME
        userDatabase = Room.databaseBuilder(context,UserDatabase::class.java, databaseName)
            .fallbackToDestructiveMigration()
            .build()
    }
}