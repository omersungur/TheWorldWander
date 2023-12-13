package com.omersungur.theworldwander.presentation.country_list

sealed class CountryListEvent {
    data object RefreshPage: CountryListEvent()
    data class OnSearchQueryChange(val query: String) : CountryListEvent()
}