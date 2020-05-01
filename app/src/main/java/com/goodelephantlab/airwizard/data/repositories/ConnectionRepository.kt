package com.goodelephantlab.airwizard.data.repositories

import android.content.Context
import com.goodelephantlab.airwizard.utils.NetworkUtil

class ConnectionRepository constructor(
    private val context: Context
) {

    fun isConnected(): Boolean {
        return NetworkUtil.checkConnection(context)
    }
}