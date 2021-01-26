package com.fiqsky.simpleretrofit

import com.fiqsky.simpleretrofit.api.CatData
import retrofit2.http.GET

const val BASE_URL = "http://aws.random.cat"

interface ApiRequest {
    @GET("/meow")
    suspend fun getRandomCat(): CatData
}