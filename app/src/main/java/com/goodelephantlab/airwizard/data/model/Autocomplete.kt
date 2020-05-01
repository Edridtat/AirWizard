package com.goodelephantlab.airwizard.data.model

import com.google.gson.annotations.SerializedName

data class Autocomplete(
    @SerializedName("country_name")
    val countryName: String,

    @SerializedName("weight")
    val weight: Int,

    @SerializedName("country_code")
    val countryCode: String,

    @SerializedName("type")
    val type: String,


    @SerializedName("name")
    val name: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("main_airport_name")
    val mainAirportName: String?,

    @SerializedName("state_code")
    val stateCode: String?,

    @SerializedName("index_strings")
    val indexStrings: ArrayList<String>,

    @SerializedName("coordinates")
    val coordinates: Coordinates
)

data class Coordinates(
    @SerializedName("lon")
    val lon: Double,

    @SerializedName("lat")
    val lat: Double
)