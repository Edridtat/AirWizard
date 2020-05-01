package com.goodelephantlab.airwizard

import android.app.Application
import com.goodelephantlab.airwizard.di.KoinInjector
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class AirWizardApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        stopKoin()
        startKoin {
            androidContext(this@AirWizardApplication)
            val injector = KoinInjector()
            modules(injector.modules)
        }
    }
}