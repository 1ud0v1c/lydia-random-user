package com.ludovic.vimont.lydiarandomuser.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ludovic.vimont.lydiarandomuser.model.User

/**
 * Provide a simple solution to transform inner object of User to a string format.
 */
class UserTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun nameToString(name: User.Name): String = gson.toJson(name)

    @TypeConverter
    fun stringToName(string: String): User.Name = gson.fromJson(string, User.Name::class.java)

    @TypeConverter
    fun locationToString(location: User.Location): String = gson.toJson(location)

    @TypeConverter
    fun stringToLocation(string: String): User.Location = gson.fromJson(string, User.Location::class.java)

    @TypeConverter
    fun loginToString(login: User.Login): String = gson.toJson(login)

    @TypeConverter
    fun stringToLogin(string: String): User.Login = gson.fromJson(string, User.Login::class.java)

    @TypeConverter
    fun idToString(id: User.Id): String = Gson().toJson(id)

    @TypeConverter
    fun stringToId(string: String): User.Id = Gson().fromJson(string, User.Id::class.java)

    @TypeConverter
    fun pictureToString(picture: User.Picture): String = gson.toJson(picture)

    @TypeConverter
    fun stringToPicture(string: String): User.Picture = gson.fromJson(string, User.Picture::class.java)
}