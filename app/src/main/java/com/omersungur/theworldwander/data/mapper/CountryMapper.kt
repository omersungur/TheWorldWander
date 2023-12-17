package com.omersungur.theworldwander.data.mapper

import com.omersungur.theworldwander.data.remote.dto.CountryDtoItem
import com.omersungur.theworldwander.domain.model.Country

fun CountryDtoItem.toCountry(): Country {

    val currency = if(currencies != null) currencies[0].name else "No Data Found!"
    val language = if(languages != null) languages[0].name else "No Data Found!"
    val latitude = if(latlng != null) latlng[0] else 0.0
    val longitude = if(latlng != null) latlng[1] else 0.0

    return Country(
        name = name,
        flag = flags.png,
        currencies = currency,
        language = language,
        region = region,
        latitude = latitude,
        longitude = longitude,
        area = area ?: 0.0,
        independent = independent ?: false
    )
}
