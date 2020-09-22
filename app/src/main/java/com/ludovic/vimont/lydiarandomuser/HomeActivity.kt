package com.ludovic.vimont.lydiarandomuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ludovic.vimont.lydiarandomuser.databinding.ActivityHomeBinding
import com.ludovic.vimont.lydiarandomuser.model.User

class HomeActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityHomeBinding
    private val userAdapter = HomeUserAdapter(getUsers())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val recyclerViewAlbums: RecyclerView = mainBinding.recyclerViewUsers
        recyclerViewAlbums.adapter = userAdapter
        recyclerViewAlbums.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun getUsers(): ArrayList<User> {
        val list = ArrayList<User>()
        list.add(User("Bob Marley", "bob.marley@gmail.com", R.drawable.user_default_picture))
        list.add(User("Francis Cabrel", "francis.cabrel@gmail.com", R.drawable.user_default_picture))
        list.add(User("John Snow", "john.snow@gmail.com", R.drawable.user_default_picture))
        return list
    }
}