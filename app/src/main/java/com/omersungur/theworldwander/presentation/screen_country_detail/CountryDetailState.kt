package com.omersungur.theworldwander.presentation.screen_country_detail

import com.omersungur.theworldwander.domain.model.CountryMongo

data class CountryDetailState(
    val selectedCountryId: String? = null,
    val selectedCountry: CountryMongo? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
