package com.omersungur.theworldwander.presentation.screen_country_detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.omersungur.theworldwander.domain.model.GalleryImage
import com.omersungur.theworldwander.presentation.screen_country_detail.components.CountryDetailContent
import com.omersungur.theworldwander.presentation.screen_country_detail.components.CountryDetailTopBar
import com.omersungur.theworldwander.presentation.screen_country_detail.components.ZoomableImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CountryDetailScreen(
    countryDetailViewModel: CountryDetailViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val state = countryDetailViewModel.countryDetailState
    val galleryState = countryDetailViewModel.galleryState
    val context = LocalContext.current
    var selectedGalleryImage by remember { mutableStateOf<GalleryImage?>(null) }

    Scaffold(
        topBar = {
            state.selectedCountry?.let {
                CountryDetailTopBar(
                    selectedCountry = it,
                    onDeleteConfirmed = {
                        countryDetailViewModel.onEvent(CountryDetailEvent.DeleteImages(
                            onSuccess = {
                                Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onError = {message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        ))
                    }
                )
            }
        },
        content = { paddingValues ->

            if (state.isLoading) {
                CircularProgressIndicator()
            }
            state.selectedCountry?.let {
                CountryDetailContent(
                    paddingValues = paddingValues,
                    country = it,
                    galleryState = galleryState,
                    onImageSelect = { uri ->
                        val imageType =
                            context.contentResolver.getType(uri)?.split("/")?.last() ?: "jpg"
                        countryDetailViewModel.addImage(
                            image = uri,
                            imageType = imageType
                        )
                    },
                    onImageClicked = { galleryImage ->
                        selectedGalleryImage = galleryImage
                    },
                    onSaveClicked = { country ->
                        countryDetailViewModel.updateCountryImage(
                            country = country.apply {

                            },
                            onSuccess = {
                                navController.popBackStack()
                            },
                            onError = { message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                )
                AnimatedVisibility(visible = selectedGalleryImage != null) {
                    Dialog(onDismissRequest = { selectedGalleryImage = null }) {
                        if (selectedGalleryImage != null) {
                            ZoomableImage(
                                selectedGalleryImage = selectedGalleryImage!!,
                                onCloseClicked = {
                                    selectedGalleryImage = null
                                },
                                onDeleteClicked = {
                                    if (selectedGalleryImage != null) {
                                        galleryState.removeImages(selectedGalleryImage!!)
                                        selectedGalleryImage = null
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

