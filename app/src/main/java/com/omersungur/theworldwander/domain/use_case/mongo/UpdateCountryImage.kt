package com.omersungur.theworldwander.domain.use_case.mongo

import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.data.repository.CountryMongoRepositoryImpl
import com.omersungur.theworldwander.domain.model.CountryMongo
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId

class UpdateCountryImage {

    suspend operator fun invoke(
        country: CountryMongo,
        selectedCountryId: String,
        countryDetailState: CountryMongo?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        callFirebaseFunctions: () -> Unit
    ) {
        val result = CountryMongoRepositoryImpl.updateCountry(
            country.apply {
                _id = ObjectId.invoke(selectedCountryId)
                images = country.images
                countryName =
                    countryDetailState?.countryName ?: "No Data Found!"
                countryRegion =
                    countryDetailState?.countryRegion ?: "No Data Found!"
                countryPopulation =
                    countryDetailState?.countryPopulation ?: "No Data Found!"
                countryLatitude =
                    countryDetailState?.countryLatitude ?: "No Data Found!"
                countryLongitude =
                    countryDetailState?.countryLongitude ?: "No Data Found!"
                countryLanguage =
                    countryDetailState?.countryLanguage ?: "No Data Found!"
                countryFlag =
                    countryDetailState?.countryFlag ?: "No Data Found!"
                countryArea =
                    countryDetailState?.countryArea ?: "No Data Found!"
                countryCurrency =
                    countryDetailState?.countryCurrency ?: "No Data Found!"
                isCountryIndependent =
                    countryDetailState?.isCountryIndependent ?: false
                countryCapital =
                    countryDetailState?.countryCapital ?: "No Data Found!"
                            ?: "No Data Found!"
                countryTimeZones =
                    countryDetailState?.countryTimeZones
                        ?: realmListOf("No Data Found")
                countryCallingCode =
                    countryDetailState?.countryCallingCode
                        ?: realmListOf("No Data Found")
                countryCode =
                    countryDetailState?.countryCode ?: "No Data Found!"
                countryBorders = countryDetailState?.countryBorders
                    ?: realmListOf("No Data Found")
            }
        )
        if (result is Resource.Success) {
            callFirebaseFunctions()
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        } else if (result is Resource.Error) {
            withContext(Dispatchers.Main) {
                onError(result.message.toString())
            }
        }
    }
}