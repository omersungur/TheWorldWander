package com.omersungur.theworldwander.domain.use_case.mongo

import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.data.repository.CountryMongoRepositoryImpl
import com.omersungur.theworldwander.domain.model.CountryMongo
import com.omersungur.theworldwander.domain.repository.CountryMongoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FilterCountries {

    private val countryMongoRepository: CountryMongoRepository = CountryMongoRepositoryImpl

    suspend operator fun invoke(filterQuery: String): Flow<Resource<List<CountryMongo>>> {
        return flow {
            var filteredList = emptyList<CountryMongo>()
            countryMongoRepository.getAllCountries("").collect { result ->
                when (result) {
                    is Resource.Success -> {
                        when (filterQuery) {
                            "Asia", "Africa", "Europe", "Americas", "Oceania", "Polar", "Antarctic Ocean", "Antarctic" -> {
                                filteredList = result.data?.filter {
                                    it.countryRegion == filterQuery
                                }?.sortedBy {
                                    it.countryRegion
                                } ?: emptyList()
                            }

                            "Population (Ascending)" -> {
                                filteredList =
                                    result.data?.sortedBy { it.countryPopulation.toInt() }
                                        ?: emptyList()
                            }

                            "Population (Descending)" -> {
                                filteredList =
                                    result.data?.sortedByDescending { it.countryPopulation.toInt() }
                                        ?: emptyList()
                            }

                            "All" -> {
                                filteredList = result.data ?: emptyList()
                            }
                        }
                        emit(Resource.Success(filteredList))
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