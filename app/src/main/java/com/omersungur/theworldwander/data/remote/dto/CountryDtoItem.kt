package com.omersungur.theworldwander.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CountryDtoItem(
    @SerializedName("alpha2Code")
    val alpha2Code: String,
    @SerializedName("alpha3Code")
    val alpha3Code: String,
    @SerializedName("altSpellings")
    val altSpellings: List<String>,
    @SerializedName("area")
    val area: Double,
    @SerializedName("borders")
    val borders: List<String>,
    @SerializedName("callingCodes")
    val callingCodes: List<String>,
    @SerializedName("capital")
    val capital: String,
    @SerializedName("cioc")
    val cioc: String,
    @SerializedName("currencies")
    val currencies: List<Currency>,
    @SerializedName("demonym")
    val demonym: String,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("flags")
    val flags: Flags,
    @SerializedName("gini")
    val gini: Double,
    @SerializedName("independent")
    val independent: Boolean,
    @SerializedName("languages")
    val languages: List<Language>,
    @SerializedName("latlng")
    val latlng: List<Double>,
    @SerializedName("name")
    val name: String,
    @SerializedName("nativeName")
    val nativeName: String,
    @SerializedName("numericCode")
    val numericCode: String,
    @SerializedName("population")
    val population: Int,
    @SerializedName("region")
    val region: String,
    @SerializedName("regionalBlocs")
    val regionalBlocs: List<RegionalBloc>,
    @SerializedName("subregion")
    val subregion: String,
    @SerializedName("timezones")
    val timezones: List<String>,
    @SerializedName("topLevelDomain")
    val topLevelDomain: List<String>,
    @SerializedName("translations")
    val translations: Translations
)