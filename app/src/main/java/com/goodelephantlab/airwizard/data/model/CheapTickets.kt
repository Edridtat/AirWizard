package com.goodelephantlab.airwizard.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheapTickets(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("error")
    val error: String,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("data")
    @Expose
    val data: Map<String, Map<String, CheapTicket>>
)

data class CheapTicket(
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
    var expiresAt: String
) {
    var destination: String? = null
    var changes: Int? = null
    var currency: String? = null
}