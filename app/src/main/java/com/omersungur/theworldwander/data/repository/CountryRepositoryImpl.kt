package com.omersungur.theworldwander.data.repository

import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.data.mapper.toCountry
import com.omersungur.theworldwander.data.remote.CountryApi
import com.omersungur.theworldwander.domain.model.Country
import com.omersungur.theworldwander.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val countryApi: CountryApi
): CountryRepository {

    override suspend fun getAllCountries(): Flow<Resource<List<Country>>> {
        return flow {
            emit(Resource.Loading(true))
            val countries = countryApi.getAllCountries()
            emit(Resource.Success(countries.map {
                it.toCountry()
            }))
        }
    }
}