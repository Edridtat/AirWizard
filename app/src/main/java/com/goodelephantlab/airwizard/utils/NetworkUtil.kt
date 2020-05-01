package com.goodelephantlab.airwizard.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Build

object NetworkUtil {

    fun checkConnection(context: Context): Boolean {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.getNetworkCapabilities(cm.activeNetwork) != null
        } else {
            (cm?.activeNetworkInfo != null)
        }
    }
}
