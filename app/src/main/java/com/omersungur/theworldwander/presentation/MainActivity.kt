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
import com.google.firebase.FirebaseApp
import com.omersungur.theworldwander.core.Constants.APP_ID
import com.omersungur.theworldwander.core.Constants.COUNTRY_DETAIL_SCREEN_ARGUMENT_ID
import com.omersungur.theworldwander.core.retryDeletingImageFromFirebase
import com.omersungur.theworldwander.core.retryUploadingImageToFirebase
import com.omersungur.theworldwander.data.local.database.ImageToDeleteDao
import com.omersungur.theworldwander.data.local.database.ImageToUploadDao
import com.omersungur.theworldwander.presentation.screen_auth.AuthenticationScreen
import com.omersungur.theworldwander.presentation.screen_country_detail.CountryDetailScreen
import com.omersungur.theworldwander.presentation.screen_country_list.CountryListScreen
import com.omersungur.theworldwander.presentation.ui.theme.TheWorldWanderTheme
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageToUploadDao: ImageToUploadDao

    @Inject
    lateinit var imageToDeleteDao: ImageToDeleteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        FirebaseApp.initializeApp(this)
        setContent {
            val navController = rememberNavController()
            TheWorldWanderTheme(dynamicColor = false) {
                NavHost(
                    startDestination = getStartDestination(),
                    navController = navController
                ) {
                    composable(route = Screen.AuthScreen.route) {
                        AuthenticationScreen {
                            navController.popBackStack()
                            navController.navigate(Screen.CountryListScreen.route)
                        }
                    }
                    composable(
                        route = Screen.CountryListScreen.route
                    ) {
                        CountryListScreen(navController = navController) {
                            navController.navigate(Screen.CountryDetailScreen.passCountryId(it))
                        }
                    }
                    composable(
                        route = Screen.CountryDetailScreen.route,
                        arguments = listOf(
                            navArgument(
                                name = COUNTRY_DETAIL_SCREEN_ARGUMENT_ID
                            ) {
                                type = NavType.StringType
                                defaultValue = null
                                nullable = true

                            }
                        )
                    ) {
                        CountryDetailScreen(navController = navController)
                    }
                }
            }
        }
        // cleanupCheck(scope = lifecycleScope, imageToUploadDao, imageToDeleteDao)
    }

    private fun getStartDestination(): String {
        val user = App.create(APP_ID).currentUser
        return if (user != null && user.loggedIn) {
            Screen.CountryListScreen.route
        } else {
            Screen.AuthScreen.route
        }
    }
}

private fun cleanupCheck(
    scope: CoroutineScope,
    imageToUploadDao: ImageToUploadDao,
    imageToDeleteDao: ImageToDeleteDao
) {
    scope.launch(Dispatchers.IO) {
        val result = imageToUploadDao.getAllImages()
        result.forEach { imageToUpload ->
            retryUploadingImageToFirebase(
                imageToUpload = imageToUpload,
                onSuccess = {
                    scope.launch(Dispatchers.IO) {
                        imageToUploadDao.cleanupImage(imageToUpload.id)
                    }
                }
            )
        }
        val result2 = imageToDeleteDao.getAllImages()
        result2.forEach { imageToDelete ->
            retryDeletingImageFromFirebase(
                imageToDelete = imageToDelete,
                onSuccess = {
                    scope.launch(Dispatchers.IO) {
                        imageToDeleteDao.cleanupImage(imageId = imageToDelete.id)
                    }
                }
            )
        }
    }
}
