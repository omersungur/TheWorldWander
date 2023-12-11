package com.omersungur.theworldwander.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.omersungur.theworldwander.presentation.ui.theme.TheWorldWanderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheWorldWanderTheme {
                GetCountries()
            }
        }
    }
}

@Composable
fun GetCountries() {
    val viewModel: CountryViewModel = hiltViewModel()
    viewModel.getAllCountries()
}