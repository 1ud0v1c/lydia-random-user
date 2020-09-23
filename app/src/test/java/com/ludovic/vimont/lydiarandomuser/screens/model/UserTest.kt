package com.ludovic.vimont.lydiarandomuser.screens.model

import android.os.Build
import com.ludovic.vimont.lydiarandomuser.model.User
import com.ludovic.vimont.lydiarandomuser.screens.MockModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class UserTest {
    private lateinit var user: User

    @Before
    fun setUp() {
        user = MockModel.buildUser("John", "Snow", "john.snow@gmail.com")
    }

    @Test
    fun testGetCountryName() {
        Assert.assertEquals("France", user.getCountryName())
        user = MockModel.buildUser("John", "Snow", "john.snow@gmail.com", "AU")
        Assert.assertEquals("Australia", user.getCountryName())
    }
}