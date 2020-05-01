package com.goodelephantlab.airwizard.data.network.api

import com.goodelephantlab.airwizard.data.model.CalendarTickets
import com.goodelephantlab.airwizard.data.model.CheapTickets
import com.goodelephantlab.airwizard.data.model.LatestTickets
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketsApi {

    @GET("/v1/prices/cheap")
    fun getCheapest(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("depart_date") departDate: String,
        @Query("return_date") returnDate: String? = null
    ): Single<CheapTickets>

    @GET("/v1/prices/calendar")
    fun getCalendarTickets(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("depart_date") departDate: String,
        @Query("return_date") returnDate: String? = null
    ): Single<CalendarTickets>

    //todo пока не используется
    @GET("/v2/prices/latest")
    fun getLatest(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("beginning_of_period") beginningOfPeriod: String,
        @Query("one_way") return_date: Boolean = false,
        @Query("period_type") periodType: String = "month"
    ): Single<LatestTickets>


}