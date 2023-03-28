package com.example.mobile_cinema_lab1.usecases

import androidx.lifecycle.MutableLiveData
import com.example.mobile_cinema_lab1.TroubleShooting
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.LoginRequestBody
import com.example.mobile_cinema_lab1.network.models.LoginResponse
import com.example.mobile_cinema_lab1.network.repositories.AuthRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IAuthRepository
import kotlinx.coroutines.Job

class SignInUseCase(
    private val loginData: LoginRequestBody,
    private val liveDataForResult: MutableLiveData<ApiResponse<LoginResponse>>
) : BaseUseCase() {
    private val authRepository: IAuthRepository = AuthRepository()

    suspend operator fun invoke(): Job {
        return baseRequest(
            liveDataForResult,
            TroubleShooting.coroutinesErrorHandler,
            authRepository.login(loginData)
        )
    }
}
