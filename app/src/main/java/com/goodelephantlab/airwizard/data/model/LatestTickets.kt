package com.goodelephantlab.airwizard.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//todo пока не используется
data class LatestTickets(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val errorMessage: String,

    @SerializedName("data")
    @Expose
    val data: ArrayList<LatestTicket>
)

data class LatestTicket(
    @SerializedName("value")
    var price: Int,

    @SerializedName("trip_class")
    var tripClass: Int,

    @SerializedName("show_to_affiliates")
    var showToAffiliates: Boolean,

    @SerializedName("return_at")
    var returnAt: String,

    @SerializedName("origin")
    var origin: String,

    @SerializedName("destination")
    var destination: String,

    @SerializedName("departure_at")
    var departureAt: String,

    @SerializedName("gate")
    var gate: String,

    @SerializedName("duration")
    var duration: Int,

    @SerializedName("distance")
    var distance: Int,

    @SerializedName("actual")
    var actual: Boolean,

    @SerializedName("number_of_changes")
    var changes: Int
)