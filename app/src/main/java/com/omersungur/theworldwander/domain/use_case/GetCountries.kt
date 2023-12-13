package com.omersungur.theworldwander.domain.use_case

import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.domain.model.Country
import com.omersungur.theworldwander.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow

class GetCountries(
    private val countryRepository: CountryRepository,
) {

    suspend operator fun invoke(): Flow<Resource<List<Country>>> {
        return countryRepository.getAllCountries()
    }
}