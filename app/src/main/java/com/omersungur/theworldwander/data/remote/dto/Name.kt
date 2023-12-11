package com.omersungur.theworldwander.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("common")
    val common: String,
    @SerializedName("nativeName")
    val nativeName: NativeName,
    @SerializedName("official")
    val official: String
)