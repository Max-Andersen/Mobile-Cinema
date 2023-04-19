package com.example.mobile_cinema_lab1.domain.usecases

import com.example.mobile_cinema_lab1.datasource.network.models.RegisterRequestBody
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.IAuthRepository
import com.example.mobile_cinema_lab1.repositories.AuthRepository

class SignUpUseCase(
    private val registerData: RegisterRequestBody,
    //private val liveDataForResult: MutableLiveData<ApiResponse<LoginResponse>>
)  {//: BaseUseCase()
    private val authRepository: IAuthRepository = AuthRepository()

    operator fun invoke() = authRepository.register(registerData)

//    suspend operator fun invoke(): Job {
//        return baseRequest(
//            liveDataForResult,
//            TroubleShooting.coroutinesErrorHandler,
//            authRepository.register(registerData)
//        )
//    }
}