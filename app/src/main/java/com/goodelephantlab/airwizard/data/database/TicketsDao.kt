package com.goodelephantlab.airwizard.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goodelephantlab.airwizard.data.database.databasemodel.FlightDate
import com.goodelephantlab.airwizard.data.database.databasemodel.FlightPoint
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface TicketsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoint(flightPoint: FlightPoint)

    @Query("SELECT * FROM FlightPoint WHERE definition = :def")
    fun getPoint(def: Int): Maybe<FlightPoint>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDate(date: FlightDate)

    @Query("SELECT * FROM FlightDate WHERE definition = :def")
    fun getDate(def: Int): Single<List<FlightDate>>

    @Query("DELETE FROM FlightDate WHERE definition = :def")
    fun deleteDate(def: Int)
}
