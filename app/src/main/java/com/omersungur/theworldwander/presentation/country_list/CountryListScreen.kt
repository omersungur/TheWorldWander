package com.omersungur.theworldwander.presentation.country_list

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.omersungur.theworldwander.presentation.country_list.components.CountryContent
import com.omersungur.theworldwander.presentation.country_list.components.CustomProgressBar

@Composable
fun CountryListScreen(
    countryListViewModel: CountryListViewModel = hiltViewModel(),
) {
    val state = countryListViewModel.state

//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(items = state.countryList) { country ->
//            CountryRow(country = country)
//            Spacer(modifier = Modifier.height(25.dp))
//        }
//    }

    Scaffold(content = {

        if (state.isLoading) {
            CustomProgressBar()
        }

        CountryContent(
            paddingValues = it,
            countries = state.countryList
        )

    })
}

//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(state.countryList.size) { i ->
//            val country = state.countryList[i]
//            CountryRow(country = country)
//        }
//    }
