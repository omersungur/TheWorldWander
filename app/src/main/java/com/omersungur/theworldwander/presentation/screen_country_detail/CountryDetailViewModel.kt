package com.omersungur.theworldwander.presentation.screen_country_detail

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.omersungur.theworldwander.core.Constants.COUNTRY_DETAIL_SCREEN_ARGUMENT_ID
import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.data.repository.CountryMongoRepositoryImpl
import com.omersungur.theworldwander.domain.model.CountryMongo
import com.omersungur.theworldwander.domain.model.GalleryImage
import com.omersungur.theworldwander.domain.model.GalleryState
import com.omersungur.theworldwander.domain.use_case.mongo.CountryMongoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val countryMongoUseCases: CountryMongoUseCases
) : ViewModel() {

    var countryDetailState by mutableStateOf(CountryDetailState())
        private set

    var galleryState = GalleryState()

    init {
        getCountryIdArgument()
        getSelectedCountry()
    }

    fun onEvent(event: CountryDetailEvent) {
        when (event) {
            is CountryDetailEvent.AddImage -> {
                // it will be fixed later
            }

            is CountryDetailEvent.DeleteImages -> {
                deleteImagesRelatedToCountry(
                    onSuccess = event.onSuccess,
                    onError = event.onError
                )
            }
        }
    }

    private fun getCountryIdArgument() {
        countryDetailState = countryDetailState.copy(
            selectedCountryId = savedStateHandle.get<String>(COUNTRY_DETAIL_SCREEN_ARGUMENT_ID)
        )
    }

    private fun getSelectedCountry() {
        viewModelScope.launch {
            countryMongoUseCases.getSelectedCountry(
                selectedCountryId = countryDetailState.selectedCountryId,
                galleryState = galleryState
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        countryDetailState =
                            countryDetailState.copy(
                                selectedCountry = result.data,
                                isLoading = false
                            )
                    }

                    is Resource.Error -> {
                        countryDetailState =
                            countryDetailState.copy(error = result.message.toString())
                    }

                    is Resource.Loading -> {
                        countryDetailState = countryDetailState.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    fun addImage(image: Uri, imageType: String) {
        val remoteImagePath = "images/${FirebaseAuth.getInstance().currentUser?.uid}" +
                "/${image.lastPathSegment}-${System.currentTimeMillis()}.$imageType"

        galleryState.addImage(
            GalleryImage(
                image = image,
                remoteImagePath = remoteImagePath
            )
        )
    }

    private fun addImagesToFirebase() {
        countryMongoUseCases.addImagesToFirebase(
            galleryState = galleryState,
            scope = viewModelScope
        )
    }

    private fun deleteImagesFromFirebase(images: List<String>? = null) {
        countryMongoUseCases.deleteImagesFromFirebase(
            images = images,
            scope = viewModelScope,
            galleryState = galleryState
        )
    }

    fun updateCountryImage(
        country: CountryMongo,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            countryMongoUseCases.updateCountryImage(
                country = country,
                selectedCountryId = countryDetailState.selectedCountryId!!,
                countryDetailState = countryDetailState.selectedCountry,
                onSuccess = onSuccess,
                onError = onError,
                callFirebaseFunctions = {
                    addImagesToFirebase()
                    deleteImagesFromFirebase()
                }
            )
        }
    }

    private fun deleteImagesRelatedToCountry(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (countryDetailState.selectedCountryId != null) {
                val result =
                    CountryMongoRepositoryImpl.deleteImage(ObjectId.invoke(countryDetailState.selectedCountryId!!))
                if (result is Resource.Success) {
                    withContext(Dispatchers.Main) {
                        countryDetailState.selectedCountry?.let {
                            deleteImagesFromFirebase(it.images)
                        }
                        onSuccess()
                    }
                } else if (result is Resource.Error) {
                    withContext(Dispatchers.Main) {
                        onError(result.message.toString())
                    }
                }
            }
        }
    }
}