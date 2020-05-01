package com.goodelephantlab.airwizard.di.modules

import androidx.room.Room
import com.goodelephantlab.airwizard.R
import com.goodelephantlab.airwizard.data.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataBaseModule = module {

    single {
        Room
            .databaseBuilder(
                androidApplication(), AppDatabase::class.java,
                androidApplication().getString(R.string.database)
            )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().ticketsDao() }
}
