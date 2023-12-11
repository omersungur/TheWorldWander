package com.omersungur.theworldwander.data.remote.dto


import com.google.gson.annotations.SerializedName

data class İdd(
    @SerializedName("root")
    val root: String,
    @SerializedName("suffixes")
    val suffixes: List<String>
)