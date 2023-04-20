package com.example.mobilecinemalab.domain.usecases

import com.example.mobilecinemalab.datasource.network.models.RegisterRequestBody
import com.example.mobilecinemalab.domain.repositoryinterfaces.IAuthRepository
import com.example.mobilecinemalab.repositories.AuthRepository

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