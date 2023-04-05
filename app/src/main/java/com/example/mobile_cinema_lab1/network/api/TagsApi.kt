package com.example.mobile_cinema_lab1.network.api

import com.example.mobile_cinema_lab1.network.models.Tag
import retrofit2.Response
import retrofit2.http.GET

interface TagsApi {

    @GET("tags")
    suspend fun getTags(): Response<List<Tag>>
}