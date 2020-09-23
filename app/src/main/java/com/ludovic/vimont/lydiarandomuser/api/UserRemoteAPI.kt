package com.ludovic.vimont.lydiarandomuser.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRemoteAPI {
    val api: RandomUserAPI

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(RandomUserAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(RandomUserAPI::class.java)
    }
}