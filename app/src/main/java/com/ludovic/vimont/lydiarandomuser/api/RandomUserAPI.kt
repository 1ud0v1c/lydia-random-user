package com.ludovic.vimont.lydiarandomuser.api

import com.ludovic.vimont.lydiarandomuser.model.RandomUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserAPI {
    companion object {
        const val BASE_URL = "https://randomuser.me/api/1.0/"
        const val NUMBER_OF_ITEM_PER_REQUEST = 10
        const val SEED = "lydia"
    }

    @GET(" ")
    suspend fun getUsersBatch(@Query("page") page: Int,
                              @Query("results") results: Int = NUMBER_OF_ITEM_PER_REQUEST,
                              @Query("seed") seed: String = SEED): Response<RandomUserResponse>
}