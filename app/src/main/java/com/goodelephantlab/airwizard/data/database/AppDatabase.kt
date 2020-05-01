package com.goodelephantlab.airwizard.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goodelephantlab.airwizard.data.database.databasemodel.FlightDate
import com.goodelephantlab.airwizard.data.database.databasemodel.FlightPoint

@Database(entities = [FlightPoint::class, FlightDate::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ticketsDao(): TicketsDao

}
