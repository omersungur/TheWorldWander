package com.omersungur.theworldwander.presentation.country_list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.omersungur.theworldwander.domain.model.Country

@Composable
fun CountryRow(
    country: Country
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = country.name ?: "No Data Found!",
            textAlign = TextAlign.Center
        )
    }
}