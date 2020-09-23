package com.ludovic.vimont.lydiarandomuser.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(val nat: String,
                val gender: String,
                val phone: String,
                val dob: Long,
                val name: Name,
                val registered: Long,
                val location: Location,
                val login: Login,
                val cell: String,
                @PrimaryKey
                val email: String,
                val id: Id?,
                val picture: Picture) {
    @Entity
    data class Name(
        val last: String,
        val title: String,
        val first: String
    )

    @Entity
    data class Location(
        val city: String,
        val street: String,
        val postcode: String,
        val state: String
    )

    @Entity
    data class Login(
        val sha1: String,
        val username: String,
        val password: String,
        val salt: String,
        val sha256: String,
        val md5: String
    )

    @Entity
    data class Id(
        val name: String,
        val value: String
    )

    @Entity
    data class Picture(
        val thumbnail: String,
        val large: String,
        val medium: String
    )
}