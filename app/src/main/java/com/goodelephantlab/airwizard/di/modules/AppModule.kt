package com.goodelephantlab.airwizard.di.modules

import com.goodelephantlab.airwizard.utils.rx.SchedulerProviderImpl
import com.google.gson.GsonBuilder
import org.koin.dsl.module

val appModule = module {
    factory { SchedulerProviderImpl() }
    factory { GsonBuilder().create() }
}
