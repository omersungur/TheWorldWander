package com.omersungur.theworldwander.presentation.country_list

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import com.omersungur.theworldwander.presentation.base.DisplayAlertDialog
import com.omersungur.theworldwander.presentation.country_list.components.CountryContent
import com.omersungur.theworldwander.presentation.country_list.components.CountryListTopBar
import com.omersungur.theworldwander.presentation.country_list.components.CustomProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    countryListViewModel: CountryListViewModel = hiltViewModel(),
) {
    val state = countryListViewModel.state
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var openDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CountryListTopBar(
                scrollBehavior = scrollBehavior,
                onLogOutClick = { openDialog = true }
            )
        },


        content = {
            if (state.isLoading) {
                CustomProgressBar()
            }

            CountryContent(
                paddingValues = it,
                countries = state.countryList
            )

            DisplayAlertDialog(
                title = "Sign Out",
                message = "Do you want to sign out?",
                dialogOpened = openDialog,
                onDialogClosed = { openDialog = false }) {
                // TODO: SIGN OUT
            }

        })
}

//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(state.countryList.size) { i ->
//            val country = state.countryList[i]
//            CountryRow(country = country)
//        }
//    }

//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(items = state.countryList) { country ->
//            CountryRow(country = country)
//            Spacer(modifier = Modifier.height(25.dp))
//        }
//    }