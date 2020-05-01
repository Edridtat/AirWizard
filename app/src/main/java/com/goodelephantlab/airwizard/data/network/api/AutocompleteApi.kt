package com.goodelephantlab.airwizard.data.network.api

import com.goodelephantlab.airwizard.data.model.Autocomplete
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AutocompleteApi {

    @GET("/places2")
    fun getAutocomplete(
        @Query("term") term: String,
        @Query("locale") locale: String,
        @Query("types[]") type: String? = null
    ): Single<List<Autocomplete>>


}