package com.omersungur.theworldwander.presentation.screen_country_detail

sealed class CountryDetailEvent {
    data object AddImage : CountryDetailEvent()
    data class DeleteImages(val onSuccess: () -> Unit, val onError: (String) -> Unit) : CountryDetailEvent()
}