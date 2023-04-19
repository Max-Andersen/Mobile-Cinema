package com.example.mobile_cinema_lab1.domain.repositoryinterfaces

import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.datasource.network.models.LoginRequestBody
import com.example.mobile_cinema_lab1.datasource.network.models.LoginResponse
import com.example.mobile_cinema_lab1.datasource.network.models.RegisterRequestBody
import kotlinx.coroutines.flow.Flow


interface IAuthRepository {
    fun register(registerData: RegisterRequestBody): Flow<ApiResponse<LoginResponse>>

    fun login(loginData: LoginRequestBody): Flow<ApiResponse<LoginResponse>>
}