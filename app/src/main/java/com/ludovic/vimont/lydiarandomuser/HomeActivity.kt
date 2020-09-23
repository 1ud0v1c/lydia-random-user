package com.ludovic.vimont.lydiarandomuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ludovic.vimont.lydiarandomuser.databinding.ActivityHomeBinding
import com.ludovic.vimont.lydiarandomuser.helper.DataStatus
import com.ludovic.vimont.lydiarandomuser.helper.NetworkHelper

class HomeActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityHomeBinding
    private val homeViewModel = HomeViewModel()
    private val userAdapter = HomeUserAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val recyclerViewAlbums: RecyclerView = mainBinding.recyclerViewUsers
        recyclerViewAlbums.adapter = userAdapter
        recyclerViewAlbums.layoutManager = LinearLayoutManager(applicationContext)
        userAdapter.onBottomReached = {
            homeViewModel.loadUsers()
        }

        homeViewModel.isNetworkAvailable.value = NetworkHelper.isOnline(applicationContext)
        homeViewModel.loadUsers()
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