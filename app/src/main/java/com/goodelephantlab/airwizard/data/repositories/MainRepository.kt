package com.goodelephantlab.airwizard.data.repositories

import com.goodelephantlab.airwizard.data.database.TicketsDao
import com.goodelephantlab.airwizard.data.database.databasemodel.FlightDate
import com.goodelephantlab.airwizard.data.database.databasemodel.FlightPoint
import com.goodelephantlab.airwizard.data.model.CalendarTickets
import com.goodelephantlab.airwizard.data.model.CheapTickets
import com.goodelephantlab.airwizard.data.model.FlightInfo
import com.goodelephantlab.airwizard.data.model.LatestTickets
import com.goodelephantlab.airwizard.data.network.api.TicketsApi
import com.goodelephantlab.airwizard.utils.constants.PointsDefinition.DESTINATION
import com.goodelephantlab.airwizard.utils.constants.PointsDefinition.ORIGIN
import com.goodelephantlab.airwizard.utils.rx.SchedulerProviderImpl
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.functions.Function4

class MainRepository constructor(
    private val ticketsApi: TicketsApi,
    private val schedulerProvider: SchedulerProviderImpl,
    private val ticketsDao: TicketsDao
) {

    fun getPoint(definition: Int): Maybe<FlightPoint> {
        return ticketsDao.getPoint(definition)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun getDate(definition: Int): Single<FlightDate> {
        return ticketsDao.getDate(definition)
            .map {
                if (it.isNullOrEmpty()) {
                    FlightDate(definition, 0)
                } else {
                    it.first()
                }
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun saveDate(date: Long, definition: Int) {
        ticketsDao.insertDate(FlightDate(definition, date))
    }

    fun clearDate(definition: Int) {
        ticketsDao.deleteDate(definition)
    }

    fun getFlightInfoTitle(): Single<FlightInfo> {
        return Single.zip(
            getPoint(ORIGIN).toSingle(),
            getPoint(DESTINATION).toSingle(),
            getDate(ORIGIN),
            getDate(DESTINATION),
            Function4<FlightPoint?, FlightPoint?, FlightDate, FlightDate, FlightInfo> { origin, destination, departmentDate, returnDate ->
                val retDate = if (returnDate.date == 0L) {
                    null
                } else {
                    returnDate.date
                }
                FlightInfo(origin.code, destination.code, departmentDate.date, retDate)
            }
        )
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun getCheapestTickets(flightInfo: FlightInfo): Single<CheapTickets> {
        return ticketsApi
            .getCheapest(
                flightInfo.codeOrigin, flightInfo.codeDestination,
                flightInfo.getDepartmentDateString(), flightInfo.getReturnDateString()
            )
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun getCalendarTickets(flightInfo: FlightInfo): Single<CalendarTickets> {
        return ticketsApi
            .getCalendarTickets(
                flightInfo.codeOrigin, flightInfo.codeDestination,
                flightInfo.getDepartmentDateString(), flightInfo.getReturnDateString()
            )
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    //todo пока не используется, ручка возвращает информацию в странном виде
    fun getLatestTickets(flightInfo: FlightInfo): Single<LatestTickets> {
        return ticketsApi
            .getLatest(
                flightInfo.codeOrigin, flightInfo.codeDestination,
                flightInfo.getBeginningOfPeriod(), flightInfo.isOneWay()
            )
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}