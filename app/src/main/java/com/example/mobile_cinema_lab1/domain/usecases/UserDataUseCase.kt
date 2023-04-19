package com.example.mobile_cinema_lab1.domain.usecases

import com.example.mobile_cinema_lab1.datasource.network.models.UpdateUserInfo
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.IProfileRepository
import com.example.mobile_cinema_lab1.repositories.ProfileRepository
import okhttp3.MultipartBody

class UserDataUseCase {
    private val userRepository: IProfileRepository = ProfileRepository()

    fun getUserData() = userRepository.getUserData()

    fun editUserData(newData: UpdateUserInfo) = userRepository.editUserData(newData)

    fun uploadUserAvatar(avatar: MultipartBody) = userRepository.loadUserPhoto(avatar)
}
