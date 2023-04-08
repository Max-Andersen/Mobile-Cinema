package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.models.UpdateUserInfo
import com.example.mobile_cinema_lab1.network.repositories.ProfileRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IProfileRepository
import okhttp3.MultipartBody

class GetUserDataUseCase {
    private val userRepository: IProfileRepository = ProfileRepository()

    fun getUserData() = userRepository.getUserData()

    fun editUserData(newData: UpdateUserInfo) = userRepository.editUserData(newData)

    fun uploadUserAvatar(avatar: MultipartBody) = userRepository.loadUserPhoto(avatar)
}
