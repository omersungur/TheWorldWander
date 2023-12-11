package com.omersungur.theworldwander.data.remote.dto


import com.google.gson.annotations.SerializedName

data class WST(
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
)