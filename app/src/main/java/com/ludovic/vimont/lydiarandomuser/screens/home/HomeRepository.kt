package com.ludovic.vimont.lydiarandomuser.screens.home

import com.ludovic.vimont.lydiarandomuser.api.RandomUserAPI
import com.ludovic.vimont.lydiarandomuser.database.UserDao
import com.ludovic.vimont.lydiarandomuser.helper.viewmodel.StateData
import com.ludovic.vimont.lydiarandomuser.model.RandomUserResponse
import com.ludovic.vimont.lydiarandomuser.model.User
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response

class HomeRepository: KoinComponent {
    private val userDao: UserDao by inject()
    private val userRemoteApi: RandomUserAPI by inject()

    suspend fun retrieveUsers(isConnectedToNetwork: Boolean, page: Int = 1): StateData<List<User>> {
        if (!isConnectedToNetwork && userDao.count() <= 0) {
            val errorMessage = "You need to be connected to a network before performing any request."
            return StateData.error(errorMessage, ArrayList())
        }
        return if (isConnectedToNetwork) {
            fetchUsersFromRemoteAPI(page)
        } else {
            loadUsersFromDatabase(page - 1)
        }
    }

    private suspend fun loadUsersFromDatabase(page: Int): StateData<List<User>> {
        val numberOfItemsPerRequest: Int = RandomUserAPI.NUMBER_OF_ITEM_PER_REQUEST
        val offset: Int = page * numberOfItemsPerRequest
        return StateData.success(userDao.get(offset, numberOfItemsPerRequest))
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
                503 -> {
                    StateData.error("The server cannot handle the request (because it is overloaded or down for maintenance).", ArrayList())
                }
                else -> {
                    StateData.error(response.message(), ArrayList())
                }
            }
        }
        response.body()?.let { randomUserResponse ->
            val newFetchedUsers: List<User> = randomUserResponse.results
            users.addAll(newFetchedUsers)
            userDao.insert(newFetchedUsers)
        }
        return StateData.success(users)
    }
}