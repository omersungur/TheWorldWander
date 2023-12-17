package com.omersungur.theworldwander.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.omersungur.theworldwander.presentation.country_list.CountryListScreen
import com.omersungur.theworldwander.presentation.ui.theme.TheWorldWanderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            TheWorldWanderTheme(dynamicColor = false) {
                NavHost(
                    startDestination = Screen.CountryListScreen.route,
                    navController = navController
                ) {
                    composable(
                        route = Screen.CountryListScreen.route,
                        arguments = listOf(
                            navArgument(
                                name = "countryName"
                            ) {
                                type = NavType.StringType
                                defaultValue = null
                                nullable = true

                            }
                        )
                    ) {
                        CountryListScreen(navController = navController)
                    }
                }
            }
        }
    }
}