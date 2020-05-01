package com.goodelephantlab.airwizard.data.network.interceptors

import android.content.Context
import com.goodelephantlab.airwizard.R
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val urlUpdated = request.url().newBuilder()
            .addQueryParameter("token", context.getString(R.string.token))
            .build()
        request = request.newBuilder()
            .url(urlUpdated)
            .build()
        return chain.proceed(request)
    }
}