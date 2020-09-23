package com.ludovic.vimont.lydiarandomuser.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ludovic.vimont.lydiarandomuser.model.User

@Dao
interface UserDao {
    @Query("SELECT count(email) FROM user")
    fun count(): Int

    @Query("SELECT * FROM user LIMIT :limit OFFSET :offset")
    fun get(offset: Int, limit: Int): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<User>)
}