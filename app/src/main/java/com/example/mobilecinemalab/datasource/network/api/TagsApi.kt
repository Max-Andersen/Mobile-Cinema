package com.example.mobilecinemalab.datasource.network.api

import com.example.mobilecinemalab.datasource.network.models.Tag
import retrofit2.Response
import retrofit2.http.GET

interface TagsApi {

    @GET("tags")
    suspend fun getTags(): Response<List<Tag>>
}