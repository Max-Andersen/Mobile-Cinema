package com.example.mobile_cinema_lab1.usecases.repositoryinterfaces

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.LoginRequestBody
import com.example.mobile_cinema_lab1.network.models.LoginResponse
import com.example.mobile_cinema_lab1.network.models.RegisterRequestBody
import kotlinx.coroutines.flow.Flow


interface IAuthRepository {
    fun register(registerData: RegisterRequestBody): Flow<ApiResponse<LoginResponse>>

    fun login(loginData: LoginRequestBody): Flow<ApiResponse<LoginResponse>>
}