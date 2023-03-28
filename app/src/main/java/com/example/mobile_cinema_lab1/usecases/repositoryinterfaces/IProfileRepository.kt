package com.example.mobile_cinema_lab1.usecases.repositoryinterfaces

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.UpdateUserInfo
import com.example.mobile_cinema_lab1.network.models.UserInfo
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface IProfileRepository {

    fun getUserData(): Flow<ApiResponse<UserInfo>>

    fun editUserData(newUserData: UpdateUserInfo): Flow<ApiResponse<UserInfo>>

    fun loadUserPhoto(photo: MultipartBody.Part): Flow<ApiResponse<Nothing>>
}