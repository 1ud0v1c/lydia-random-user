package com.ludovic.vimont.lydiarandomuser.screens.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ludovic.vimont.lydiarandomuser.model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): AndroidViewModel(application) {
    private val detailRepository = DetailRepository(application)
    var user = MutableLiveData<User>()

    fun loadUser(email: String) {
        GlobalScope.launch {
            val fetchedUser: User = detailRepository.retrieveUser(email)
            user.postValue(fetchedUser)
        }
    }
}