package com.ludovic.vimont.lydiarandomuser.screens.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ludovic.vimont.lydiarandomuser.R
import com.ludovic.vimont.lydiarandomuser.databinding.ActivityDetailBinding
import com.ludovic.vimont.lydiarandomuser.helper.IntentHelper
import com.ludovic.vimont.lydiarandomuser.model.User
import com.ludovic.vimont.lydiarandomuser.screens.home.HomeActivity
import com.ludovic.vimont.lydiarandomuser.screens.home.HomeViewModel

class DetailActivity : AppCompatActivity() {
    companion object {
        const val FADE_IN_DURATION = 300
    }
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        detailViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(DetailViewModel::class.java)
        detailViewModel.user.observe(this) { user ->
            updateUser(user)
            bindClick(user)
        }

        intent.extras?.let { bundle ->
            if (bundle.containsKey(HomeActivity.KEY_USER_EMAIL)) {
                bundle.getString(HomeActivity.KEY_USER_EMAIL)?.apply {
                    detailViewModel.loadUser(this)
                }
            }
        }
    }

    private fun updateUser(user: User) {
        Glide.with(applicationContext)
            .load(user.picture.large)
            .placeholder(R.drawable.user_default_picture)
            .transition(DrawableTransitionOptions.withCrossFade(FADE_IN_DURATION))
            .into(detailBinding.imageViewUserPicture)

        detailBinding.textViewUserName.text = user.getUserCompleteName()
        val genderId: Int = if (user.gender == "male") {
            R.drawable.ic_male
        } else {
            R.drawable.ic_female
        }
        detailBinding.textViewUserName.setCompoundDrawablesWithIntrinsicBounds(0, 0, genderId, 0)

        detailBinding.textViewUserNationality.text = getString(R.string.detail_activity_user_country, user.getCountryName())
        detailBinding.textViewUserDateOfBirth.text = getString(R.string.detail_activity_user_dob, user.getDateOfBirth())
        detailBinding.textViewUserPhone.text = getString(R.string.detail_activity_user_phone, user.phone)
        detailBinding.textViewUserCell.text = getString(R.string.detail_activity_user_cell, user.cell)

        detailBinding.textViewUserDateOfRegistration.text = getString(R.string.detail_activity_user_registration_date, user.getRegistrationDate())
        detailBinding.textViewUserEmail.text = getString(R.string.detail_activity_user_email, user.email)
        detailBinding.textViewUserLogin.text = getString(R.string.detail_activity_user_username, user.login.username)
        detailBinding.textViewUserPassword.text = getString(R.string.detail_activity_user_password, user.login.password)

        detailBinding.textViewUserLocation.text = user.location.toString()
    }

    private fun bindClick(user: User) {
        detailBinding.textViewUserCell.setOnClickListener {
            IntentHelper.launchCall(applicationContext, user.cell)
        }
        detailBinding.textViewUserPhone.setOnClickListener {
            IntentHelper.launchCall(applicationContext, user.phone)
        }
        detailBinding.textViewUserLocation.setOnClickListener {
            IntentHelper.launchMaps(applicationContext, user.getGoogleMapsURL())
        }
    }
}