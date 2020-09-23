package com.ludovic.vimont.lydiarandomuser.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ludovic.vimont.lydiarandomuser.model.User

@Database(entities = [User::class], version = 1)
@TypeConverters(UserTypeConverter::class)
abstract class UserDatabase: RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "random_user_database"
    }

    abstract fun userDao(): UserDao
}