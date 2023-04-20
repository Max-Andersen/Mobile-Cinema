package com.example.mobilecinemalab.domain.usecases

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.LoginRequestBody
import com.example.mobilecinemalab.datasource.network.models.LoginResponse
import com.example.mobilecinemalab.domain.repositoryinterfaces.IAuthRepository
import com.example.mobilecinemalab.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignInUseCase(
    private val loginData: LoginRequestBody,
    //private val liveDataForResult: MutableLiveData<ApiResponse<LoginResponse>>
)  { // :BaseUseCase
    private val authRepository: IAuthRepository = AuthRepository()

    operator fun invoke(): Flow<ApiResponse<LoginResponse>> {//: Job

        return authRepository.login(loginData)
//        return baseRequest(
//            liveDataForResult,
//            TroubleShooting.coroutinesErrorHandler,
//            authRepository.login(loginData)
//        )
    }

//    override suspend fun <T, U> baseRequest(data: U): Flow<T> {
//        authRepository.login(data)
//    }
}
