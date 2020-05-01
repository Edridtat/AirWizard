package com.goodelephantlab.airwizard.data.model

import com.google.gson.annotations.SerializedName

//todo пока не используется, нужно будет для запроса всех актуальных билетов
data class AirLinesInfo(

    @SerializedName("name")
    val error: String,

    @SerializedName("code")
    val currency: String
)