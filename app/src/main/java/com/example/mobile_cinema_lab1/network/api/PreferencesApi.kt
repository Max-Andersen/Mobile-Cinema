package com.example.mobile_cinema_lab1.network.api

import com.example.mobile_cinema_lab1.network.models.Tags
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface PreferencesApi {

    @GET("preferences")
    suspend fun getUserPreferences(): Response<List<Tags>>

    @PUT("preferences")
    suspend fun updateUserPreferences(@Body newPreferences: List<Tags>): Response<Nothing>
}