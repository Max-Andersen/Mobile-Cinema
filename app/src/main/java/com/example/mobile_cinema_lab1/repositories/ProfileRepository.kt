package com.example.mobile_cinema_lab1.repositories

import com.example.mobile_cinema_lab1.datasource.network.Network
import com.example.mobile_cinema_lab1.datasource.network.api.ProfileApi
import com.example.mobile_cinema_lab1.datasource.network.models.UpdateUserInfo
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.IProfileRepository
import okhttp3.MultipartBody

class ProfileRepository: IProfileRepository, BaseRepository() {
    private val profileApi: ProfileApi = Network.getProfileApi()

    override fun getUserData() = apiRequestFlow { profileApi.getUserInfo() }

    override fun editUserData(newUserData: UpdateUserInfo) =
        apiRequestFlow { profileApi.updateUserInfo(newUserData) }

    override fun loadUserPhoto(photo: MultipartBody) =
        apiRequestFlow { profileApi.uploadProfileImage(photo) }
}