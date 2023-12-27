package com.omersungur.theworldwander.presentation.screen_country_detail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.omersungur.theworldwander.domain.model.CountryMongo
import com.omersungur.theworldwander.presentation.base.DisplayAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailTopBar(
    selectedCountry: CountryMongo,
    onDeleteConfirmed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Country Detail")
        }, actions = {
            DeleteImagesRelatedToCountry(
                selectedCountry = selectedCountry,
                onDeleteConfirmed = onDeleteConfirmed
            )
        }
    )
}

@Composable
fun DeleteImagesRelatedToCountry(
    selectedCountry: CountryMongo,
    onDeleteConfirmed: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = {
                Text(text = "Delete Images")
            },
            onClick = {
                openDialog = true
                expanded = false
            }
        )
    }
    DisplayAlertDialog(
        title = "Delete",
        message = "Do you want to permanently delete images which belongs to this country? '${selectedCountry.countryName}'?",
        dialogOpened = openDialog,
        onDialogClosed = { openDialog = false },
        onYesClicked = onDeleteConfirmed
    )
    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Overflow Menu Icon",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}
