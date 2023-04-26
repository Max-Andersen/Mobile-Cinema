package com.example.mobilecinemalab.datasource.network.api


import com.example.mobilecinemalab.datasource.network.models.LoginRequestBody
import com.example.mobilecinemalab.datasource.network.models.LoginResponse
import com.example.mobilecinemalab.datasource.network.models.RegisterRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun register(@Body requestBody: RegisterRequestBody): Response<LoginResponse>

    @POST("auth/login")
    suspend fun login(@Body requestBody: LoginRequestBody): Response<LoginResponse>

    @POST("auth/refresh")
    suspend fun refresh(@Header("Authorization") refreshToken: String): Response<LoginResponse>
}