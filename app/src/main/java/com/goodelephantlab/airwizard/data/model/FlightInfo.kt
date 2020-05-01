package com.goodelephantlab.airwizard.data.model

import java.text.SimpleDateFormat
import java.util.*

data class FlightInfo(
    val codeOrigin: String,
    val codeDestination: String,
    val departmentDate: Long,
    val returnDate: Long?
) {
    private val formatterDate = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
    private val formatterBeginningOfPeriod = SimpleDateFormat("YYYY-MM-01", Locale.getDefault())

    fun getDepartmentDateString(): String {
        return formatterDate.format(departmentDate)
    }

    fun getReturnDateString(): String? {
        returnDate?.let {
            return formatterDate.format(returnDate)
        }
        return null
    }

    fun getBeginningOfPeriod(): String {
        return formatterBeginningOfPeriod.format(departmentDate)
    }

    fun isOneWay() = (returnDate != null)
}
