package com.omersungur.theworldwander.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Eng(
    @SerializedName("f")
    val f: String,
    @SerializedName("m")
    val m: String
)