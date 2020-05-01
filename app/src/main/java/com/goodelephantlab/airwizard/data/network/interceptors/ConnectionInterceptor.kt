package com.goodelephantlab.airwizard.data.network.interceptors

import com.goodelephantlab.airwizard.data.network.exception.NoNetworkConnectionException
import com.goodelephantlab.airwizard.data.repositories.ConnectionRepository
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor(
    private val connectionRepository: ConnectionRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectionRepository.isConnected()) {
            throw NoNetworkConnectionException()
        }
        return chain.proceed(chain.request())
    }
}