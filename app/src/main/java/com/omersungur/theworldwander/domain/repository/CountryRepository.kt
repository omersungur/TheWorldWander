package com.omersungur.theworldwander.domain.repository

import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {

    suspend fun getAllCountries(): Flow<Resource<List<Country>>>

}