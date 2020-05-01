package com.goodelephantlab.airwizard.di

import com.goodelephantlab.airwizard.di.modules.*

class KoinInjector {

    val modules = listOf(
        appModule,
        NetworkModule().networkModule,
        dataModule,
        dataBaseModule,
        viewModelModule
    )
}