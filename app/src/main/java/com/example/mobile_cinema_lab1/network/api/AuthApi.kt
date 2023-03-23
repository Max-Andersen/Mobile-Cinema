package com.example.mobile_cinema_lab1.network.api

import com.example.mobile_cinema_lab1.network.models.LoginRequestBody
import com.example.mobile_cinema_lab1.network.models.LoginResponse
import com.example.mobile_cinema_lab1.network.models.RegisterRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("register")
    suspend fun register(@Body requestBody: RegisterRequestBody): Response<LoginResponse>

    @POST("login")
    suspend fun login(@Body requestBody: LoginRequestBody): Response<LoginResponse>

    @POST("refresh")
    suspend fun refresh(@Body refreshToken: String): Response<LoginResponse>
}