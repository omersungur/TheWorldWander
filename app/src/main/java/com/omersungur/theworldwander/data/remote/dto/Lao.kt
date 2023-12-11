package com.omersungur.theworldwander.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Lao(
    @SerializedName("common")
    val common: String,
    @SerializedName("official")
    val official: String
)