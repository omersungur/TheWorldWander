package com.omersungur.theworldwander.data.remote

import com.omersungur.theworldwander.data.remote.dto.CountryDto
import retrofit2.Response
import retrofit2.http.GET

interface CountryApi {

    @GET("all")
    suspend fun getAllCountries(): CountryDto
}