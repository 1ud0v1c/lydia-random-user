package com.ludovic.vimont.lydiarandomuser.screens.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ludovic.vimont.lydiarandomuser.R
import com.ludovic.vimont.lydiarandomuser.databinding.ActivityHomeBinding
import com.ludovic.vimont.lydiarandomuser.helper.DataStatus
import com.ludovic.vimont.lydiarandomuser.helper.network.ConnectionLiveData
import com.ludovic.vimont.lydiarandomuser.helper.network.NetworkHelper
import com.ludovic.vimont.lydiarandomuser.screens.detail.DetailActivity

class HomeActivity : AppCompatActivity() {
    companion object {
        const val KEY_USER_EMAIL = "home_activity_user_email"
    }
    private val userAdapter = HomeUserAdapter(ArrayList())
    private lateinit var mainBinding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val recyclerViewAlbums: RecyclerView = mainBinding.recyclerViewUsers
        recyclerViewAlbums.adapter = userAdapter
        recyclerViewAlbums.layoutManager = LinearLayoutManager(applicationContext)
        userAdapter.onItemClick = { clickedUser ->
            val intent = Intent(applicationContext, DetailActivity::class.java)
            intent.putExtra(KEY_USER_EMAIL, clickedUser.email)
            startActivity(intent)
        }
        userAdapter.onBottomReached = {
            homeViewModel.loadUsers()
        }

        connectionLiveData = ConnectionLiveData(applicationContext)
        homeViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(HomeViewModel::class.java)
        updateNetworkStatus()

        homeViewModel.loadUsers()
        updateUsersList()
    }

    private fun updateNetworkStatus() {
        homeViewModel.isNetworkAvailable.value = NetworkHelper.isOnline(applicationContext)
        connectionLiveData.observe(this) {
            homeViewModel.isNetworkAvailable.value = it
        }
    }

    private fun updateUsersList() {
        homeViewModel.stateDataUsers.observe(this) { stateData ->
            when (stateData.status) {
                DataStatus.SUCCESS -> {
                    userAdapter.addUsers(stateData.data!!)
                }
                DataStatus.ERROR -> {
                    displayError(stateData.errorMessage!!)
                }
            }
        }
    }

    private fun displayError(errorMessage: String) {
        val snackBar: Snackbar = Snackbar.make(mainBinding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(getString(R.string.action_retry)) {
            homeViewModel.isNetworkAvailable.value = NetworkHelper.isOnline(applicationContext)
            homeViewModel.loadUsers()
            snackBar.dismiss()
        }
        val snackBarView: View = snackBar.view
        val snackBarTextView: TextView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
        snackBarTextView.maxLines = 3
        snackBar.show()
    }
}