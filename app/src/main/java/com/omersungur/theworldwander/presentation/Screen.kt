package com.omersungur.theworldwander.presentation

sealed class Screen(val route: String) {
    data object CountryListScreen : Screen("country_list_screen")
}