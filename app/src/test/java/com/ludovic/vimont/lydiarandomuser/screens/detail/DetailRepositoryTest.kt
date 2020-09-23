package com.ludovic.vimont.lydiarandomuser.screens.detail

import android.content.Context
import android.os.Build
import android.os.SystemClock
import androidx.test.core.app.ApplicationProvider
import com.ludovic.vimont.lydiarandomuser.api.RandomUserAPI
import com.ludovic.vimont.lydiarandomuser.database.UserDao
import com.ludovic.vimont.lydiarandomuser.database.UserDatabase
import com.ludovic.vimont.lydiarandomuser.database.UserLocal
import com.ludovic.vimont.lydiarandomuser.helper.DataStatus
import com.ludovic.vimont.lydiarandomuser.helper.StateData
import com.ludovic.vimont.lydiarandomuser.model.User
import com.ludovic.vimont.lydiarandomuser.screens.MockModel
import com.ludovic.vimont.lydiarandomuser.screens.home.HomeRepository
import kotlinx.coroutines.*
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Disposable
import org.junit.Assert
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*
import kotlin.collections.ArrayList

@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class DetailRepositoryTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val firstName = "John"
    private val lastName = "Doe"
    private val email = "john.doe@gmail.com"
    private lateinit var detailRepository: DetailRepository

    @Before
    fun setUp() {
        val userDao: UserDao = UserLocal(context).userDatabase.userDao()
        runBlocking(Dispatchers.IO) {
            userDao.insert(getUsers())
        }
        detailRepository = DetailRepository(context)
    }

    private fun getUsers(): List<User> {
        val users = ArrayList<User>()
        users.add(MockModel.buildUser(firstName, lastName, email))
        return users
    }

    @Test
    fun testRetrieveUser() {
        runBlocking {
            val user: User = detailRepository.retrieveUser(email)
            Assert.assertNotNull(user)
            Assert.assertEquals(firstName, user.name.first)
            Assert.assertEquals(lastName, user.name.last)
        }
    }
}