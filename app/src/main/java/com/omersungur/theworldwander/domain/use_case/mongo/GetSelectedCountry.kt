package com.omersungur.theworldwander.domain.use_case.mongo

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.core.fetchImagesFromFirebase
import com.omersungur.theworldwander.data.repository.CountryMongoRepositoryImpl
import com.omersungur.theworldwander.domain.model.CountryMongo
import com.omersungur.theworldwander.domain.model.GalleryImage
import com.omersungur.theworldwander.domain.model.GalleryState
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.mongodb.kbson.ObjectId

class GetSelectedCountry {

    suspend operator fun invoke(
        selectedCountryId: String?,
        galleryState: GalleryState
    ): Flow<Resource<CountryMongo>> {
        return flow {
            if (selectedCountryId != null) {
                CountryMongoRepositoryImpl.getSelectedCountry(ObjectId.invoke(selectedCountryId))
                    .catch {
                        emit(Resource.Error(Exception("An error occurred!").message.toString()))
                    }.collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                val countryDetail = CountryMongo().apply {
                                    images = result.data?.images ?: realmListOf("")
                                    countryName = result.data?.countryName ?: "No Data Found!"
                                    countryArea = result.data?.countryArea ?: "No Data Found!"
                                    countryFlag = result.data?.countryFlag ?: "No Data Found!"
                                    countryRegion = result.data?.countryRegion ?: "No Data Found!"
                                    countryPopulation =
                                        result.data?.countryPopulation ?: "No Data Found!"
                                    countryCurrency =
                                        result.data?.countryCurrency ?: "No Data Found!"
                                    countryLatitude =
                                        result.data?.countryLatitude ?: "No Data Found!"
                                    countryLongitude =
                                        result.data?.countryLongitude ?: "No Data Found!"
                                    countryLanguage =
                                        result.data?.countryLanguage ?: "No Data Found!"
                                    isCountryIndependent =
                                        result.data?.isCountryIndependent ?: false
                                    countryCapital = result.data?.countryCapital ?: "No Data Found!"
                                    countryCode = result.data?.countryCode ?: "No Data Found!"
                                    countryTimeZones = result.data?.countryTimeZones ?: realmListOf(
                                        "No Data Found"
                                    )
                                    countryCallingCode = result.data?.countryCallingCode
                                        ?: realmListOf("No Data Found")
                                    countryBorders =
                                        result.data?.countryBorders ?: realmListOf("No Data Found!")

                                    fetchImagesFromFirebase(
                                        remoteImagePaths = result.data?.images ?: listOf(),
                                        onImageDownload = { downloadedImage ->
                                            galleryState.addImage(
                                                GalleryImage(
                                                    image = downloadedImage,
                                                    remoteImagePath = extractImagePath(
                                                        fullImageUrl = downloadedImage.toString()
                                                    )
                                                )
                                            )
                                        }
                                    )
                                }

                                emit(Resource.Success(countryDetail))
                            }

                            is Resource.Error -> {
                                emit(Resource.Error("An error occurred!"))
                            }

                            is Resource.Loading -> {
                                emit(Resource.Loading(true))
                            }
                        }
                    }
            }
        }
    }
}

private fun extractImagePath(fullImageUrl: String): String {
    val chunks = fullImageUrl.split("%2F")
    val imageName = chunks[2].split("?").first()
    return "images/${Firebase.auth.currentUser?.uid}/$imageName"
}