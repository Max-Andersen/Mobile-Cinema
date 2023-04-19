package com.example.mobile_cinema_lab1.datasource.network.api

import com.example.mobile_cinema_lab1.datasource.network.models.EpisodeShort
import retrofit2.Response
import retrofit2.http.GET

interface HistoryApi {

    @GET("history")
    suspend fun getUserHistory(): Response<List<EpisodeShort>>
}