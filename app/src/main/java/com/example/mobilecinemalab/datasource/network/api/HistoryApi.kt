package com.example.mobilecinemalab.datasource.network.api

import com.example.mobilecinemalab.datasource.network.models.EpisodeShort
import retrofit2.Response
import retrofit2.http.GET

interface HistoryApi {

    @GET("history")
    suspend fun getUserHistory(): Response<List<EpisodeShort>>
}