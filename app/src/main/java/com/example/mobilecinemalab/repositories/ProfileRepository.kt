package com.example.mobilecinemalab.repositories

import com.example.mobilecinemalab.datasource.network.Network
import com.example.mobilecinemalab.datasource.network.api.ProfileApi
import com.example.mobilecinemalab.datasource.network.models.UpdateUserInfo
import com.example.mobilecinemalab.domain.repositoryinterfaces.IProfileRepository
import okhttp3.MultipartBody

class ProfileRepository: IProfileRepository, BaseRepository() {
    private val profileApi: ProfileApi = Network.getProfileApi()

    override fun getUserData() = apiRequestFlow { profileApi.getUserInfo() }

    override fun editUserData(newUserData: UpdateUserInfo) =
        apiRequestFlow { profileApi.updateUserInfo(newUserData) }

    override fun loadUserPhoto(photo: MultipartBody.Part) =
        apiRequestFlow { profileApi.uploadProfileImage(photo) }
}