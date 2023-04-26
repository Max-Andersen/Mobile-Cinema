package com.example.mobilecinemalab.domain.usecases

import com.example.mobilecinemalab.datasource.network.models.UpdateUserInfo
import com.example.mobilecinemalab.domain.repositoryinterfaces.IProfileRepository
import com.example.mobilecinemalab.repositories.ProfileRepository
import okhttp3.MultipartBody

class UserDataUseCase {
    private val userRepository: IProfileRepository = ProfileRepository()

    fun getUserData() = userRepository.getUserData()

    fun editUserData(newData: UpdateUserInfo) = userRepository.editUserData(newData)

    fun uploadUserAvatar(avatar: MultipartBody.Part) = userRepository.loadUserPhoto(avatar)
}
