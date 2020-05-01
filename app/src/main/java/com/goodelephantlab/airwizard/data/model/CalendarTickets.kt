package com.goodelephantlab.airwizard.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CalendarTickets(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("error")
    val error: String,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("data")
    @Expose
    val data: Map<String, CalendarTicket>
)

data class CalendarTicket(
    @SerializedName("price")
    var price: Int,

    @SerializedName("airline")
    var airline: String,

    @SerializedName("flight_number")
    var flightNumber: Int,

    @SerializedName("departure_at")
    var departureAt: String,

    @SerializedName("return_at")
    var returnAt: String,

    @SerializedName("expires_at")
    var expiresAt: String,

    @SerializedName("origin")
    var origin: String,

    @SerializedName("destination")
    var destination: String,

    @SerializedName("transfers")
    var transfers: Int
) {
    var currency: String? = null

}