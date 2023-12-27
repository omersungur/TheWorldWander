package com.omersungur.theworldwander.presentation.screen_country_list

import com.omersungur.theworldwander.domain.model.Country
import com.omersungur.theworldwander.domain.model.CountryMongo

data class CountryListState(
    val countryList: List<Country> = emptyList(),
    val countryMongoList: List<CountryMongo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val isRefreshingForSwipe: Boolean = false,
    val searchQuery: String = "",
)
