package com.ludovic.vimont.lydiarandomuser.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ludovic.vimont.lydiarandomuser.model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    private val detailRepository = DetailRepository()
    var user = MutableLiveData<User>()

    fun loadUser(email: String) {
        GlobalScope.launch {
            val fetchedUser: User = detailRepository.retrieveUser(email)
            user.postValue(fetchedUser)
        }
    }
}