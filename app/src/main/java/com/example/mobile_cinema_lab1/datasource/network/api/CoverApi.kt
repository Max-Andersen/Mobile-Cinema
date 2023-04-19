package com.example.mobile_cinema_lab1.datasource.network.api

import com.example.mobile_cinema_lab1.datasource.network.models.CoverImage
import retrofit2.Response
import retrofit2.http.GET

interface CoverApi {

    @GET("cover")
    suspend fun getCover(): Response<CoverImage>
}