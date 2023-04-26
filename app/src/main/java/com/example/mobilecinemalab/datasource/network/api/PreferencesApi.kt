package com.example.mobilecinemalab.datasource.network.api


import com.example.mobilecinemalab.datasource.network.models.Tag
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface PreferencesApi {

    @GET("preferences")
    suspend fun getUserPreferences(): Response<List<Tag>>

    @PUT("preferences")
    suspend fun updateUserPreferences(@Body newPreferences: List<Tag>): Response<Nothing>
}