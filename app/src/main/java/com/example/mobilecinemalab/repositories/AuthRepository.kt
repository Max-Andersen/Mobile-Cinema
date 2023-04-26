package com.example.mobilecinemalab.repositories

import com.example.mobilecinemalab.datasource.network.Network
import com.example.mobilecinemalab.datasource.network.api.AuthApi
import com.example.mobilecinemalab.datasource.network.models.LoginRequestBody
import com.example.mobilecinemalab.datasource.network.models.RegisterRequestBody
import com.example.mobilecinemalab.domain.repositoryinterfaces.IAuthRepository

class AuthRepository: IAuthRepository, BaseRepository() {
    private val authApi: AuthApi = Network.getAuthApi()

    override fun register(registerData: RegisterRequestBody) =
        apiRequestFlow { authApi.register(registerData) }

    override fun login(loginData: LoginRequestBody) = apiRequestFlow { authApi.login(loginData) }

}