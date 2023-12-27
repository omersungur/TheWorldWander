package com.omersungur.theworldwander.domain.use_case.mongo

import com.omersungur.theworldwander.data.repository.CountryMongoRepositoryImpl
import com.omersungur.theworldwander.domain.model.Country
import com.omersungur.theworldwander.domain.model.CountryMongo
import com.omersungur.theworldwander.domain.repository.CountryMongoRepository
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList

class AddCountriesInMongoDB {

    private val countryMongoRepository: CountryMongoRepository = CountryMongoRepositoryImpl

    suspend operator fun invoke(countryList: List<Country>) {

            countryList.forEach { country ->
                val addMongoDB = CountryMongo().apply {
                    countryName = country.name ?: "No Data Found!"
                    countryRegion = country.region ?: "No Data Found!"
                    countryPopulation = country.population.toString()
                    countryLatitude = country.latitude.toString()
                    countryLongitude = country.longitude.toString()
                    countryLanguage = country.language ?: "No Data Found!"
                    countryFlag = country.flag ?: "No Data Found!"
                    countryArea = country.area.toString()
                    countryCurrency = country.currencies.toString()
                    isCountryIndependent = country.independent ?: false
                    countryCapital = country.capital ?: "No Data Found!"
                    countryTimeZones =
                        country.timeZone?.toRealmList() ?: realmListOf("No Data Found")
                    countryCallingCode =
                        country.callingCode?.toRealmList() ?: realmListOf("No Data Found")
                    countryCode = country.code ?: "No Data Found!"
                    countryBorders = country.borders?.toRealmList() ?: realmListOf("No Data Found")
                }
                countryMongoRepository.addCountry(addMongoDB)
            }
    }
}