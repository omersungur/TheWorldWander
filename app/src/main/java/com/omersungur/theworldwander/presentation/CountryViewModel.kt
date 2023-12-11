package com.omersungur.theworldwander.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omersungur.theworldwander.domain.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {

    fun getAllCountries() = viewModelScope.launch {
        countryRepository.getAllCountries().collect {
            println(it.data)
        }
    }
}