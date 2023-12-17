package com.omersungur.theworldwander.presentation.country_list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.omersungur.theworldwander.domain.model.Country

@Composable
fun CountryContent(
    paddingValues: PaddingValues,
    countries: List<Country>,
    onItemClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        items(items = countries) { country ->
            CountryRow(
                country = country,
                onItemClicked = onItemClicked
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}