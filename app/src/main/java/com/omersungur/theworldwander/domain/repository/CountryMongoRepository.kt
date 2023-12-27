package com.omersungur.theworldwander.domain.repository

import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.domain.model.CountryMongo
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface CountryMongoRepository {

    fun configureTheRealm()
    fun getAllCountries(query: String): Flow<Resource<List<CountryMongo>>>
    fun getSelectedCountry(countryId: ObjectId): Flow<Resource<CountryMongo>>
    suspend fun addCountry(country: CountryMongo): Resource<CountryMongo>
    suspend fun updateCountry(country: CountryMongo): Resource<CountryMongo>
    suspend fun deleteImage(countryId: ObjectId): Resource<Boolean>
    suspend fun deleteAllImagesInMongoDB(): Resource<Boolean>
}