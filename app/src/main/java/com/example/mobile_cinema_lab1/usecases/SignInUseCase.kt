package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.LoginRequestBody
import com.example.mobile_cinema_lab1.network.models.LoginResponse
import com.example.mobile_cinema_lab1.network.repositories.AuthRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IAuthRepository
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
