package com.omersungur.theworldwander.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("iso639_1")
    val iso6391: String,
    @SerializedName("iso639_2")
    val iso6392: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nativeName")
    val nativeName: String
)