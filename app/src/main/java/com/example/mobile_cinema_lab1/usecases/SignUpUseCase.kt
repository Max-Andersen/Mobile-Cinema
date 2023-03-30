package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.models.RegisterRequestBody
import com.example.mobile_cinema_lab1.network.repositories.AuthRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IAuthRepository

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