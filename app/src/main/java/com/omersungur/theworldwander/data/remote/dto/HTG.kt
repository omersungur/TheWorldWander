package com.omersungur.theworldwander.data.remote.dto


import com.google.gson.annotations.SerializedName

data class HTG(
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
)