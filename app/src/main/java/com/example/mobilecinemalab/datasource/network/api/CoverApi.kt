package com.example.mobilecinemalab.datasource.network.api

import com.example.mobilecinemalab.datasource.network.models.CoverImage
import retrofit2.Response
import retrofit2.http.GET

interface CoverApi {

    @GET("cover")
    suspend fun getCover(): Response<CoverImage>
}