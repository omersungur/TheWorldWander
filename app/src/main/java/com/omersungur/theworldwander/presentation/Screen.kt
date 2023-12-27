package com.omersungur.theworldwander.presentation

import com.omersungur.theworldwander.core.Constants.COUNTRY_DETAIL_SCREEN_ARGUMENT_ID

sealed class Screen(val route: String) {
    data object AuthScreen : Screen("authentication_screen")
    data object CountryListScreen : Screen("country_list_screen")
    data object CountryDetailScreen: Screen("country_detail_screen?$COUNTRY_DETAIL_SCREEN_ARGUMENT_ID={$COUNTRY_DETAIL_SCREEN_ARGUMENT_ID}") {
        fun passCountryId(countryId: String) =
            "country_detail_screen?$COUNTRY_DETAIL_SCREEN_ARGUMENT_ID=$countryId"
    }
}