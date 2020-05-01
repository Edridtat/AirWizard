package com.goodelephantlab.airwizard.di.modules

import android.content.Context
import com.goodelephantlab.airwizard.data.network.api.AutocompleteApi
import com.goodelephantlab.airwizard.data.network.api.TicketsApi
import com.goodelephantlab.airwizard.data.network.interceptors.ConnectionInterceptor
import com.goodelephantlab.airwizard.data.network.interceptors.TokenInterceptor
import com.goodelephantlab.airwizard.data.repositories.ConnectionRepository
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule {

    companion object {
        const val DEFAULT_RESPONSE_TIMEOUT_SECONDS = 10L
        const val AUTOCOMPLETE_BASE_URL = "autocomplete_base_url"
        const val TICKETS_BASE_URL = "tickets_base_url"
    }

    val networkModule: org.koin.core.module.Module = module {
        single { provideCheckConnectionInterceptor(get()) }
        single { provideTokenInterceptor(get()) }
        single { provideGsonConverterFactory(get()) }

        factory(named(TICKETS_BASE_URL)) { provideTicketsUrlRetrofit(get(), get(), get()) }
        single { provideTicketsApi(get(named(TICKETS_BASE_URL))) }

        factory(named(AUTOCOMPLETE_BASE_URL)) { provideRetrofitAutocompleteUrl(get(), get()) }
        single { provideAutocompleteApi(get(named(AUTOCOMPLETE_BASE_URL))) }
    }

    private fun provideTicketsApi(retrofit: Retrofit): TicketsApi {
        return retrofit.create(TicketsApi::class.java)
    }

    private fun provideAutocompleteApi(retrofit: Retrofit): AutocompleteApi {
        return retrofit.create(AutocompleteApi::class.java)
    }

    private fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    private fun provideCheckConnectionInterceptor(connectionRepository: ConnectionRepository): ConnectionInterceptor {
        return ConnectionInterceptor(connectionRepository)
    }

    private fun provideTokenInterceptor(context: Context): TokenInterceptor {
        return TokenInterceptor(context)
    }

    private fun provideTicketsUrlRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        connectionInterceptor: ConnectionInterceptor,
        tokenInterceptor: TokenInterceptor
    ): Retrofit {
        val interceptors = arrayOf(connectionInterceptor, tokenInterceptor)

        val client = buildOkHttpClient(DEFAULT_RESPONSE_TIMEOUT_SECONDS, interceptors)
        return buildRetrofit(client, gsonConverterFactory)
    }

    private fun buildOkHttpClient(timeout: Long, interceptors: Array<Interceptor>): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)

        interceptors.forEach { builder.addInterceptor(it) }
        return builder.build()
    }

    private fun buildRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.travelpayouts.com")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun provideRetrofitAutocompleteUrl(
        gsonConverterFactory: GsonConverterFactory,
        connectionInterceptor: ConnectionInterceptor
    ): Retrofit {
        val interceptors = arrayOf<Interceptor>(connectionInterceptor)
        val client = buildOkHttpClient(DEFAULT_RESPONSE_TIMEOUT_SECONDS, interceptors)
        return buildRetrofitAutocompleteUrl(client, gsonConverterFactory)
    }

    private fun buildRetrofitAutocompleteUrl(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://autocomplete.travelpayouts.com")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}
