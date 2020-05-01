package com.goodelephantlab.airwizard.data.database.databasemodel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class FlightPoint(
    @PrimaryKey val definition: Int,
    val code: String,
    val name: String
) : Parcelable
