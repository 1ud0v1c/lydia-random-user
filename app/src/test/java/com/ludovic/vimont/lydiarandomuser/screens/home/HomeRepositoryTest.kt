package com.ludovic.vimont.lydiarandomuser.screens.home

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.ludovic.vimont.lydiarandomuser.api.RandomUserAPI
import com.ludovic.vimont.lydiarandomuser.database.UserDao
import com.ludovic.vimont.lydiarandomuser.database.UserDatabase
import com.ludovic.vimont.lydiarandomuser.database.UserLocal
import com.ludovic.vimont.lydiarandomuser.helper.DataStatus
import com.ludovic.vimont.lydiarandomuser.helper.StateData
import com.ludovic.vimont.lydiarandomuser.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class HomeRepositoryTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val numberOfItemPerRequest: Int = RandomUserAPI.NUMBER_OF_ITEM_PER_REQUEST
    private lateinit var homeRepository: HomeRepository

    @Before
    fun setUp() {
        homeRepository = HomeRepository(context)
    }

    @After
    fun tearDown() {
        val userDatabase: UserDatabase = UserLocal(context).userDatabase
        runBlocking(Dispatchers.IO) {
            userDatabase.clearAllTables()
        }
    }

    @Test
    fun testRetrieveUsers() {
        runBlocking {
            withContext(Dispatchers.Default) {
                val users: List<User> = homeRepository.retrieveUsers(true).data!!
                Assert.assertEquals(numberOfItemPerRequest, users.size)
                val firstUser: User = users[0]

                val newUsers: List<User> = homeRepository.retrieveUsers(true, 2).data!!
                val newFirstUser: User = newUsers[0]
                Assert.assertNotEquals(firstUser.name, newFirstUser.name)
            }
        }
    }

    @Test
    fun testRetrieveUsersNoInternet() {
        runBlocking {
            withContext(Dispatchers.Default) {
                val stateData: StateData<List<User>> = homeRepository.retrieveUsers(false)
                Assert.assertEquals(DataStatus.ERROR, stateData.status)
                Assert.assertEquals(0, stateData.data?.size)
                Assert.assertNotNull(stateData.errorMessage)
            }
        }
    }

    @Test
    fun testRetrieveUsersThenNoInternet() {
        runBlocking {
            withContext(Dispatchers.Default) {
                val noNetworkNoDataInDb: List<User> = homeRepository.retrieveUsers(false).data!!
                Assert.assertEquals(0, noNetworkNoDataInDb.size)

                val users: List<User> = homeRepository.retrieveUsers(true).data!!
                Assert.assertEquals(numberOfItemPerRequest, users.size)

                val noNetworkLoadUsersFromDB: List<User> = homeRepository.retrieveUsers(false).data!!
                Assert.assertEquals(numberOfItemPerRequest, noNetworkLoadUsersFromDB.size)
            }
        }
    }
}