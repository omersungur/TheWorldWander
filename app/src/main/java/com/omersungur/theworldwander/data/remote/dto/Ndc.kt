package com.omersungur.theworldwander.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Ndc(
    @SerializedName("common")
    val common: String,
    @SerializedName("official")
    val official: String
)