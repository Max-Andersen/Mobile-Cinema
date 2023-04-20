package com.example.mobilecinemalab.domain.repositoryinterfaces

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.UpdateUserInfo
import com.example.mobilecinemalab.datasource.network.models.UserInfo
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.ResponseBody

interface IProfileRepository {

    fun getUserData(): Flow<ApiResponse<UserInfo>>

    fun editUserData(newUserData: UpdateUserInfo): Flow<ApiResponse<UserInfo>>

    fun loadUserPhoto(photo: MultipartBody.Part): Flow<ApiResponse<ResponseBody>>
}