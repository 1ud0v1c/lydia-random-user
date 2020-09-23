package com.ludovic.vimont.lydiarandomuser.model

data class User(val nat: String,
                val gender: String,
                val phone: String,
                val dob: Long,
                val name: Name,
                val registered: Long,
                val location: Location,
                val login: Login,
                val cell: String,
                val email: String,
                val picture: Picture) {

    data class Name(
        val last: String,
        val title: String,
        val first: String
    )

    data class Location(
        val city: String,
        val street: String,
        val postcode: String,
        val state: String)

    data class Login(
        val sha1: String,
        val username: String,
        val password: String,
        val salt: String,
        val sha256: String,
        val md5: String
    )

    data class Picture(
        val thumbnail: String,
        val large: String,
        val medium: String
    )
}