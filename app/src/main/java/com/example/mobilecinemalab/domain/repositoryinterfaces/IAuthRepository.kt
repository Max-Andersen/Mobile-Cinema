package com.example.mobilecinemalab.domain.repositoryinterfaces

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.LoginRequestBody
import com.example.mobilecinemalab.datasource.network.models.LoginResponse
import com.example.mobilecinemalab.datasource.network.models.RegisterRequestBody
import kotlinx.coroutines.flow.Flow


interface IAuthRepository {

    fun register(registerData: RegisterRequestBody): Flow<ApiResponse<LoginResponse>>

    fun login(loginData: LoginRequestBody): Flow<ApiResponse<LoginResponse>>
}