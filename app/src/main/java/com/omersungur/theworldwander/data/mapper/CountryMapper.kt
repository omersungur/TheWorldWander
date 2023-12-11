package com.omersungur.theworldwander.data.mapper

import com.omersungur.theworldwander.data.remote.dto.CountryDtoItem
import com.omersungur.theworldwander.domain.model.Country

fun CountryDtoItem.toCountry(): Country {
    return Country(
        name = name.common,
        area = area,
        independent = independent
    )
}