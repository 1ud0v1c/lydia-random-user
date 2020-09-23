package com.ludovic.vimont.lydiarandomuser

import com.ludovic.vimont.lydiarandomuser.api.RandomUserAPI
import com.ludovic.vimont.lydiarandomuser.api.UserRemoteAPI
import com.ludovic.vimont.lydiarandomuser.helper.StateData
import com.ludovic.vimont.lydiarandomuser.model.RandomUserResponse
import com.ludovic.vimont.lydiarandomuser.model.User
import retrofit2.Response

class HomeUserRepository {
    private val userRemoteApi: RandomUserAPI = UserRemoteAPI().api

    suspend fun retrieveUsers(isConnectedToNetwork: Boolean, page: Int = 1): StateData<List<User>> {
        if (!isConnectedToNetwork) {
            val errorMessage = "You need to be connected to a network before performing any request."
            return StateData.error(errorMessage, ArrayList())
        }
        return fetchUsersFromRemoteAPI(page)
    }

    private suspend fun fetchUsersFromRemoteAPI(page: Int): StateData<List<User>> {
        val users = ArrayList<User>()
        val response: Response<RandomUserResponse> = userRemoteApi.getUsersBatch(page)
        if (!response.isSuccessful) {
            return when (response.code()) {
                404 -> {
                    StateData.error("The requested resource could not be found but may be available in the future.", ArrayList())
                }
                429 -> {
                    StateData.error("The user has sent too many requests in a given amount of time.", ArrayList())
                }
                else -> {
                    StateData.error(response.message(), ArrayList())
                }
            }
        }
        response.body()?.let { randomUserResponse ->
            users.addAll(randomUserResponse.results)
        }
        return StateData.success(users)
    }
}