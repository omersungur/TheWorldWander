package com.omersungur.theworldwander.presentation.screen_country_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.omersungur.theworldwander.domain.model.CountryMongo
import com.omersungur.theworldwander.presentation.screen_country_list.CountryListEvent
import com.omersungur.theworldwander.presentation.screen_country_list.CountryListViewModel

@Composable
fun CountryListContent(
    paddingValues: PaddingValues,
    countries: List<CountryMongo>,
    onItemClicked: (String) -> Unit,
    viewModel: CountryListViewModel
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.countryListState.isRefreshingForSwipe
    )
    val state = viewModel.countryListState
    
    Spacer(modifier = Modifier.height(8.dp))

    Column {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    CountryListEvent.OnSearchQueryChange(it)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = paddingValues.calculateTopPadding()),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(CountryListEvent.RefreshPage)
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp)
            ) {
                items(items = countries) { country ->
                    CountryRow(
                        country = country,
                        onItemClicked = onItemClicked,
                        viewModel = viewModel
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}