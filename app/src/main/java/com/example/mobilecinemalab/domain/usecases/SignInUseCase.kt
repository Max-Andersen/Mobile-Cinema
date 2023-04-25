package com.example.mobilecinemalab.domain.usecases

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.LoginRequestBody
import com.example.mobilecinemalab.datasource.network.models.LoginResponse
import com.example.mobilecinemalab.domain.repositoryinterfaces.IAuthRepository
import com.example.mobilecinemalab.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignInUseCase(
    private val loginData: LoginRequestBody,
)  {
    private val authRepository: IAuthRepository = AuthRepository()

    operator fun invoke(): Flow<ApiResponse<LoginResponse>> {
        return authRepository.login(loginData)
    }

}
