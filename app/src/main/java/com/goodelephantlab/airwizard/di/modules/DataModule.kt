package com.goodelephantlab.airwizard.di.modules

import com.goodelephantlab.airwizard.data.repositories.ConnectionRepository
import com.goodelephantlab.airwizard.data.repositories.MainRepository
import com.goodelephantlab.airwizard.data.repositories.PointsRepository
import org.koin.dsl.module

val dataModule = module {
    single { ConnectionRepository(get()) }
    single { MainRepository(get(), get(), get()) }
    single { PointsRepository(get(), get(), get()) }
}
