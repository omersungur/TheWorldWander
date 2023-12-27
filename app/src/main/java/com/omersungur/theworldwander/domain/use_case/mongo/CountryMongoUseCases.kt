package com.omersungur.theworldwander.domain.use_case.mongo

data class CountryMongoUseCases(
    val addCountriesInMongoDB: AddCountriesInMongoDB,
    val filterCountries: FilterCountries,
    val getAllCountriesFromMongoDB: GetAllCountriesFromMongoDB,
    val deleteAllImagesFromMongoDB: DeleteAllImagesFromMongoDB,
    val getSelectedCountry: GetSelectedCountry,
    val addImagesToFirebase: AddImagesToFirebase,
    val deleteImagesFromFirebase: DeleteImagesFromFirebase,
    val updateCountryImage: UpdateCountryImage
)