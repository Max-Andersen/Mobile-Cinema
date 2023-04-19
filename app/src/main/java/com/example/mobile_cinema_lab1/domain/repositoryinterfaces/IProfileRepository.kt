package com.example.mobile_cinema_lab1.domain.repositoryinterfaces

import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.datasource.network.models.UpdateUserInfo
import com.example.mobile_cinema_lab1.datasource.network.models.UserInfo
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.ResponseBody

interface IProfileRepository {

    fun getUserData(): Flow<ApiResponse<UserInfo>>

    fun editUserData(newUserData: UpdateUserInfo): Flow<ApiResponse<UserInfo>>

    fun loadUserPhoto(photo: MultipartBody): Flow<ApiResponse<ResponseBody>>
}