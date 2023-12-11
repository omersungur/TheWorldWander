package com.omersungur.theworldwander.domain.model

data class Country(
    val name: String?,
    val currencies: String?,
    val language: String,
    val region: String?,
    val latitude: Double?,
    val longitude: Double,
    val area: Double?,
    val independent: Boolean?
)

