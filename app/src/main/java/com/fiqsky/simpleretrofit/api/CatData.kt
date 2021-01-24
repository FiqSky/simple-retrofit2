package com.fiqsky.simpleretrofit.api

import com.google.gson.annotations.SerializedName

data class CatData(
        @SerializedName("file")
        val img: String
)