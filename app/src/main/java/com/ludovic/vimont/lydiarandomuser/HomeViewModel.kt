package com.ludovic.vimont.lydiarandomuser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ludovic.vimont.lydiarandomuser.helper.DataStatus
import com.ludovic.vimont.lydiarandomuser.helper.StateData
import com.ludovic.vimont.lydiarandomuser.model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val userRepository = HomeUserRepository(application.applicationContext)
    private var page: Int = 1
    var isNetworkAvailable = MutableLiveData<Boolean>()
    var stateDataUsers = MutableLiveData<StateData<List<User>>>()

    fun loadUsers() {
        GlobalScope.launch {
            val isConnected: Boolean = isNetworkAvailable.value ?: false
            val stateData: StateData<List<User>> = userRepository.retrieveUsers(isConnected, page)
            if (stateData.status == DataStatus.SUCCESS) {
                page += 1
            }
            stateDataUsers.postValue(stateData)
        }
    }
}