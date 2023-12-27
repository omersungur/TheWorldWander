package com.omersungur.theworldwander.presentation.screen_country_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.theworldwander.connectivity.ConnectivityObserver
import com.omersungur.theworldwander.connectivity.NetworkConnectivityObserver
import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.data.repository.CountryMongoRepositoryImpl
import com.omersungur.theworldwander.data.repository.DataStoreRepository
import com.omersungur.theworldwander.domain.model.CountryMongo
import com.omersungur.theworldwander.domain.use_case.CountryUseCases
import com.omersungur.theworldwander.domain.use_case.mongo.CountryMongoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val countryUseCases: CountryUseCases,
    private val countryMongoUseCases: CountryMongoUseCases,
    private val dataStoreRepository: DataStoreRepository,
    private val connectivity: NetworkConnectivityObserver,
) : ViewModel() {

    var countryListState by mutableStateOf(CountryListState())
        private set
    private var searchJob: Job? = null
    private var network by mutableStateOf(ConnectivityObserver.Status.Unavailable)

    init {
        readLastClickedCountry()
        viewModelScope.launch {
            connectivity.observe().collect { network = it }
        }
        getAllCountriesFromMongoDB(query = "")
        viewModelScope.launch(Dispatchers.Main) {
            delay(1000)
            // If MongoDB is empty then get data from api.
            if (countryListState.countryMongoList.isEmpty()) {
                getAllCountriesFromAPI(fetchForTheFirstTime = true)
            }
        }
    }

    fun onEvent(event: CountryListEvent) {
        when (event) {
            is CountryListEvent.RefreshPage -> {
                countryListState = countryListState.copy(isRefreshingForSwipe = true)
                getAllCountriesFromAPI(fetchForTheFirstTime = false)
                countryListState = countryListState.copy(isRefreshingForSwipe = false)
            }

            is CountryListEvent.OnSearchQueryChange -> {
                countryListState = countryListState.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getAllCountriesFromMongoDB(query = event.query)
                }
            }
        }
    }

    fun saveLastClickedCountry(lastClickedCountry: String) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveLastClickedCountry(lastClickedCountry)
        }

    private fun readLastClickedCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.readLastClickedCountry.collect {
                println(it)
            }
        }
    }

    private fun getAllCountriesFromAPI(fetchForTheFirstTime: Boolean) {
        viewModelScope.launch {
            countryUseCases.getCountries().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        countryListState = countryListState.copy(
                            countryList = result.data ?: emptyList(),
                            isLoading = false
                        )
                        if (fetchForTheFirstTime) {
                            addCountriesInMongoDB()
                        } else {
                            updateAllCountriesInfo()
                        }
                    }

                    is Resource.Error -> {
                        countryListState = countryListState.copy(
                            error = result.message.toString()
                        )
                    }

                    is Resource.Loading -> {
                        countryListState = countryListState.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    private fun getAllCountriesFromMongoDB(query: String) {
        viewModelScope.launch {
            countryMongoUseCases.getAllCountriesFromMongoDB(query).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        countryListState = countryListState.copy(
                            countryMongoList = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        countryListState = countryListState.copy(
                            error = result.message.toString()
                        )
                    }

                    is Resource.Loading -> {
                        countryListState = countryListState.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    fun filterCountries(filter: String) {
        viewModelScope.launch {
            countryMongoUseCases.filterCountries(filter).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        countryListState = countryListState.copy(
                            countryMongoList = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        countryListState = countryListState.copy(
                            error = result.message.toString()
                        )
                    }

                    is Resource.Loading -> {
                        countryListState = countryListState.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    private fun addCountriesInMongoDB() {
        viewModelScope.launch(Dispatchers.IO) {
            countryMongoUseCases.addCountriesInMongoDB(countryListState.countryList)
        }
    }

    private fun updateAllCountriesInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val updateMongoDB = CountryMongo().apply {

                countryListState.countryMongoList.forEach {
                    _id  = it._id
                    println("${it._id} ${it.countryName}")
                }
            }
            countryListState.countryList.forEach { country ->
                updateMongoDB.apply {

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
                //println("${updateMongoDB._id} ${updateMongoDB.countryName}")
            }
            CountryMongoRepositoryImpl.updateCountry(
                updateMongoDB
            )
        }
    }

    fun deleteAllImagesInMongoDB(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            if (network == ConnectivityObserver.Status.Available) {
                countryMongoUseCases.deleteAllImagesFromMongoDB(
                    onSuccess = onSuccess,
                    onError = onError,
                    scope = viewModelScope
                )

            } else {
                onError(Exception("No Internet Connection.").message.toString())
            }
        }
    }
}