package com.example.mobile_cinema_lab1.network.repositories

import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.network.api.ProfileApi
import com.example.mobile_cinema_lab1.network.apiRequestFlow
import com.example.mobile_cinema_lab1.network.models.UpdateUserInfo
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IProfileRepository
import okhttp3.MultipartBody

class ProfileRepository: IProfileRepository {
    private val profileApi: ProfileApi = Network.getProfileApi()

    override fun getUserData() = apiRequestFlow { profileApi.getUserInfo() }

    override fun editUserData(newUserData: UpdateUserInfo) =
        apiRequestFlow { profileApi.updateUserInfo(newUserData) }

    override fun loadUserPhoto(photo: MultipartBody.Part) =
        apiRequestFlow { profileApi.uploadProfileImage(photo) }
}