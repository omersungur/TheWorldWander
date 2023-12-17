package com.omersungur.theworldwander.domain.model

data class Country(
    val name: String?,
    val flag: String?,
    val currencies: String?,
    val population: Int?,
    val language: String,
    val region: String?,
    val latitude: Double?,
    val longitude: Double,
    val area: Double?,
    val independent: Boolean?
)

