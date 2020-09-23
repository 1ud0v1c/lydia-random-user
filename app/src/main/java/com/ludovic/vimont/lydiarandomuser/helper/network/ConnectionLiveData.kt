package com.ludovic.vimont.lydiarandomuser.helper.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData

/**
 * Tools to update the current connectivity status automatically
 * @see: https://medium.com/@alexzaitsev/android-viewmodel-check-network-connectivity-state-7c028a017cd4
 */
class ConnectionLiveData(private val context: Context): LiveData<Boolean>() {
    companion object {
        val TAG: String = ConnectionLiveData::class.java.simpleName
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            postValue(NetworkHelper.isOnline(context))
        }
    }

    override fun onActive() {
        super.onActive()
        context.registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onInactive() {
        super.onInactive()
        try {
            context.unregisterReceiver(networkReceiver)
        } catch (e: Exception) {
            Log.d(TAG, "exception: ${e.message}", e)
        }
    }
}