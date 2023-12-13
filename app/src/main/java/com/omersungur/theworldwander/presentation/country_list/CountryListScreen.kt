package com.omersungur.theworldwander.presentation.country_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import com.omersungur.theworldwander.presentation.country_list.components.CountryRow

@Composable
fun CountryListScreen(
    countryListViewModel: CountryListViewModel = hiltViewModel()
) {
    val state = countryListViewModel.state

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = state.countryList) { country ->
            CountryRow(country = country)
        }
    }
}

//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(state.countryList.size) { i ->
//            val country = state.countryList[i]
//            CountryRow(country = country)
//        }
//    }
