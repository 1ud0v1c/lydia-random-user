package com.ludovic.vimont.lydiarandomuser

import android.app.Application
import androidx.room.Room
import com.ludovic.vimont.lydiarandomuser.api.RandomUserAPI
import com.ludovic.vimont.lydiarandomuser.database.UserDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LydiaApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val networkModule: Module = module {
            fun provideRetrofit(): Retrofit {
                return Retrofit.Builder()
                    .baseUrl(RandomUserAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            fun providerRandomUserAPI(retrofit: Retrofit) = retrofit.create(RandomUserAPI::class.java)

            single {
                provideRetrofit()
            }
            factory {
                providerRandomUserAPI(get())
            }
        }

        val databaseModule: Module = module {
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

        startKoin {
            androidContext(this@LydiaApplication)
            modules(listOf(networkModule, databaseModule))
        }
    }
}