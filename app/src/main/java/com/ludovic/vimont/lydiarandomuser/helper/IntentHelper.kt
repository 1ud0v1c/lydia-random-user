package com.ludovic.vimont.lydiarandomuser.helper

import android.content.Context
import android.content.Intent
import android.net.Uri

object IntentHelper {
    fun launchCall(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        intent.flags += Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun launchMaps(context: Context, uri: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(uri)
        )
        intent.flags += Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}