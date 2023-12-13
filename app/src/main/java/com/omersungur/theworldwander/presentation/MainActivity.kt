package com.omersungur.theworldwander.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omersungur.theworldwander.presentation.country_list.CountryListScreen
import com.omersungur.theworldwander.presentation.country_list.CountryListViewModel
import com.omersungur.theworldwander.presentation.ui.theme.TheWorldWanderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            TheWorldWanderTheme {
                NavHost(startDestination = Screen.CountryListScreen.route, navController = navController) {
                    composable(route = Screen.CountryListScreen.route) {
                        CountryListScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun GetCountries() {
    val viewModel: CountryListViewModel = hiltViewModel()
    viewModel.getAllCountries()
}