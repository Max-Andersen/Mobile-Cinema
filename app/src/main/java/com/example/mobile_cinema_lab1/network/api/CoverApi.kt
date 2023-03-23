package com.example.mobile_cinema_lab1.network.api

import com.example.mobile_cinema_lab1.network.models.CoverResponse
import retrofit2.Response
import retrofit2.http.GET

interface CoverApi {

    @GET("cover")
    suspend fun getCovers(): Response<CoverResponse>
}