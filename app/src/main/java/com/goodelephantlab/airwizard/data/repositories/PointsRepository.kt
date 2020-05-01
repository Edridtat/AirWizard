package com.goodelephantlab.airwizard.data.repositories

import com.goodelephantlab.airwizard.data.database.TicketsDao
import com.goodelephantlab.airwizard.data.database.databasemodel.FlightPoint
import com.goodelephantlab.airwizard.data.model.Autocomplete
import com.goodelephantlab.airwizard.data.network.api.AutocompleteApi
import com.goodelephantlab.airwizard.utils.rx.SchedulerProviderImpl
import io.reactivex.Single

class PointsRepository constructor(
    private val autocompleteApi: AutocompleteApi,
    private val schedulerProvider: SchedulerProviderImpl,
    private val ticketsDao: TicketsDao
) {

    fun searchCities(input: String): Single<List<Autocomplete>> {
        return autocompleteApi.getAutocomplete(input, "ru", "city")
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun savePoint(flightPoint: FlightPoint) {
        ticketsDao.insertPoint(flightPoint)
    }
}