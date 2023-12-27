package com.omersungur.theworldwander.domain.use_case.mongo

import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.data.repository.CountryMongoRepositoryImpl
import com.omersungur.theworldwander.domain.model.CountryMongo
import com.omersungur.theworldwander.domain.repository.CountryMongoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllCountriesFromMongoDB {

    private val countryMongoRepository: CountryMongoRepository = CountryMongoRepositoryImpl

    suspend operator fun invoke(query: String): Flow<Resource<List<CountryMongo>>> {
        return flow {
            var queriedList: List<CountryMongo>
            countryMongoRepository.getAllCountries(query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        queriedList = result.data?.filter {
                            it.countryName.startsWith(query, ignoreCase = true)
                        } ?: emptyList()

                        emit(Resource.Success(queriedList))
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