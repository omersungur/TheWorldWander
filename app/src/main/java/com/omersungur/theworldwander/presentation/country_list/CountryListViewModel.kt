package com.omersungur.theworldwander.presentation.country_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.domain.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {

    var state by mutableStateOf(CountryListState())
        private set

    init {
        getAllCountries()
    }

    fun onEvent(event: CountryListEvent) {
        when (event) {
            is CountryListEvent.RefreshPage -> {

            }

            is CountryListEvent.OnSearchQueryChange -> {

            }
        }
    }

    fun getAllCountries() {
        viewModelScope.launch {
            countryRepository.getAllCountries().collect { result ->
                state = when (result) {
                    is Resource.Success -> {
                        state.copy(countryList = result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        state.copy(error = "An error occurred!")
                    }

                    is Resource.Loading -> {
                        state.copy(isLoading = true)
                    }
                }
            }
        }
    }
}