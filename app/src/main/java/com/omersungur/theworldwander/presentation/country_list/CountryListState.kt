package com.omersungur.theworldwander.presentation.country_list

import com.omersungur.theworldwander.domain.model.Country

data class CountryListState(
    val countryList: List<Country> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
)
