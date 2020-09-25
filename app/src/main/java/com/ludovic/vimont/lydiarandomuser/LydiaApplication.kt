package com.ludovic.vimont.lydiarandomuser

import android.app.Application
import androidx.room.Room
import com.ludovic.vimont.lydiarandomuser.api.OkHttpBuilder
import com.ludovic.vimont.lydiarandomuser.api.RandomUserAPI
import com.ludovic.vimont.lydiarandomuser.database.UserDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Entry point of the Koin library to make dependency injection.
 * Useful to simplify repositories access to the network or the database.
 */
class LydiaApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val networkModule: Module = buildNetworkModule()
        val databaseModule: Module = buildDatabaseModule()

        startKoin {
            androidContext(this@LydiaApplication)
            modules(listOf(networkModule, databaseModule))
        }
    }

    private fun buildNetworkModule(): Module {
        return module {
            fun provideRetrofit(): Retrofit {
                return Retrofit.Builder()
                    .baseUrl(RandomUserAPI.BASE_URL)
                    .client(OkHttpBuilder.getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            fun providerRandomUserAPI(retrofit: Retrofit) =
                retrofit.create(RandomUserAPI::class.java)

            single {
                provideRetrofit()
            }
            single {
                providerRandomUserAPI(get())
            }
        }
    }

    private fun buildDatabaseModule(): Module {
        return module {
            single {
                val databaseName: String = UserDatabase.DATABASE_NAME
                Room.databaseBuilder(androidContext(), UserDatabase::class.java, databaseName)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            single {
                get<UserDatabase>().userDao()
            }
        }
    }
}