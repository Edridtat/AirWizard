package com.goodelephantlab.airwizard.ui

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import com.goodelephantlab.airwizard.base.BaseViewModel
import com.goodelephantlab.airwizard.data.database.databasemodel.FlightPoint
import com.goodelephantlab.airwizard.data.model.*
import com.goodelephantlab.airwizard.data.repositories.MainRepository
import com.goodelephantlab.airwizard.data.repositories.PointsRepository
import com.goodelephantlab.airwizard.utils.constants.PointsDefinition.DESTINATION
import com.goodelephantlab.airwizard.utils.constants.PointsDefinition.ORIGIN
import com.goodelephantlab.airwizard.utils.rx.SchedulerProviderImpl
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val mainRepository: MainRepository,
    private val pointsRepository: PointsRepository,
    private val schedulerProvider: SchedulerProviderImpl
) : BaseViewModel() {

    val originLD: MutableLiveData<FlightPoint> = MutableLiveData()
    val destinationLD: MutableLiveData<FlightPoint> = MutableLiveData()
    val pointsLD: MutableLiveData<List<Autocomplete>> = MutableLiveData()
    val departDateLD: MutableLiveData<Long> = MutableLiveData()
    val returnDateLD: MutableLiveData<Long> = MutableLiveData()
    val flightInfoTitleLD: MutableLiveData<String> = MutableLiveData()
    val cheapestTicketsLD: MutableLiveData<ArrayList<CheapTicket>> = MutableLiveData()
    val calendarTicketsLD: MutableLiveData<ArrayList<CalendarTicket>> = MutableLiveData()
    val enableSearchLd: MutableLiveData<Boolean> = MutableLiveData()
    val showProgressBarLD: MutableLiveData<Boolean> = MutableLiveData()


    fun fetchOrigin() {
        mainRepository
            .getPoint(ORIGIN)
            .subscribe({ origin ->
                origin?.let {
                    originLD.postValue(it)
                }
            }, {
                handleError(it)
            }).connect()
    }

    fun fetchDestination() {
        mainRepository
            .getPoint(DESTINATION)
            .subscribe({ destination ->
                destination?.let {
                    destinationLD.postValue(it)
                }
            }, {
                handleError(it)
            }).connect()
    }

    fun fetchDepartmentDate() {
        mainRepository
            .getDate(ORIGIN)
            .subscribe({
                departDateLD.postValue(it.date)
            }, {
                handleError(it)
            }).connect()
    }

    fun fetchReturnDate() {
        mainRepository
            .getDate(DESTINATION)
            .subscribe({
                returnDateLD.postValue(it.date)
            }, {
                handleError(it)
            }).connect()
    }

    fun searchPoints(searchView: SearchView) {
        Flowable.create<String>({
            searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        it.onNext(query)
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        it.onNext(newText)
                        return false
                    }
                }
            )
        }, BackpressureStrategy.LATEST)
            .debounce(200L, TimeUnit.MILLISECONDS, schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .flatMap { text ->
                return@flatMap if (text == "") {
                    Flowable.just(emptyList<Autocomplete>())
                } else {
                    pointsRepository.searchCities(text).toFlowable()
                }
            }
            .subscribe({
                pointsLD.postValue(it)
            }, {
                handleError(it)
            }).connect()
    }

    fun savePoint(autocomplete: Autocomplete, definition: Int) {
        pointsRepository.savePoint(FlightPoint(definition, autocomplete.code, autocomplete.name))
        when (definition) {
            ORIGIN -> fetchOrigin()
            DESTINATION -> fetchDestination()
        }
    }

    fun addDate(year: Int, month: Int, day: Int, definition: Int) {
        val selectedDate =
            Calendar.getInstance(Locale.getDefault()).apply { set(year, month, day) }.timeInMillis
        when (definition) {
            ORIGIN -> departDateLD.postValue(selectedDate)
            DESTINATION -> returnDateLD.postValue(selectedDate)
        }
        mainRepository.saveDate(selectedDate, definition)
    }

    fun clearReturnDate() {
        mainRepository.clearDate(DESTINATION)
        returnDateLD.postValue(0L)
    }

    fun enableBtnIfNeed() {
        enableSearchLd.postValue(
            (originLD.value != null
                    && destinationLD.value != null
                    && departDateLD.value != null
                    && departDateLD.value != 0L)
        )
    }

    fun fetchFlightInfo() {
        mainRepository
            .getFlightInfoTitle()
            .doOnSubscribe { showProgressBarLD.postValue(true) }
            .flatMap {
                val formatter = SimpleDateFormat("dd MMMM", Locale.getDefault())
                val title = StringBuilder(
                    "${it.codeOrigin} - ${it.codeDestination}, " +
                            formatter.format(it.departmentDate)
                )
                if (it.returnDate != null) {
                    title.append("- ${formatter.format(it.returnDate)}")
                }
                flightInfoTitleLD.postValue(title.toString())
                Single.zip(mainRepository.getCheapestTickets(it),
                    mainRepository.getCalendarTickets(it),
                    BiFunction<CheapTickets, CalendarTickets,
                            Pair<CheapTickets, CalendarTickets>>
                    { cheapestTickets, calendarTickets ->
                        Pair(cheapestTickets, calendarTickets)
                    })

            }
            .subscribe({ (cheapestTickets, calendarTickets) ->
                if (cheapestTickets.success) {
                    val tickets = arrayListOf<CheapTicket>()
                    cheapestTickets.data.forEach { entry ->
                        entry.value.toSortedMap().forEach {
                            it.value.changes = it.key.toInt()
                            it.value.destination = entry.key
                            it.value.currency = cheapestTickets.currency
                            tickets.add(it.value)
                        }
                    }
                    cheapestTicketsLD.postValue(tickets)
                }
                if (calendarTickets.success) {
                    val tickets = arrayListOf<CalendarTicket>()
                    calendarTickets.data.toSortedMap().forEach {
                        it.value.currency = calendarTickets.currency
                        tickets.add(it.value)
                    }
                    calendarTicketsLD.postValue(tickets)
                }
            }, {
                handleError(it)
            }).connect()
    }

    private fun handleError(it: Throwable) {
        showProgressBarLD.postValue(false)
        errorLD.postValue(it.localizedMessage)
    }
}
