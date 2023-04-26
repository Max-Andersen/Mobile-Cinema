package com.example.mobilecinemalab.datasource.network.api


import com.example.mobilecinemalab.datasource.network.models.Chat
import com.example.mobilecinemalab.datasource.network.models.UpdateUserInfo
import com.example.mobilecinemalab.datasource.network.models.UserInfo
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ProfileApi {

    @GET("profile")
    suspend fun getUserInfo(): Response<UserInfo>

    @PATCH("profile")
    suspend fun updateUserInfo(@Body newData: UpdateUserInfo): Response<UserInfo>

    @Multipart
    @POST("profile/avatar")
    suspend fun uploadProfileImage(@Part part: MultipartBody.Part): Response<ResponseBody>

    @GET("chats")
    suspend fun getUserChats(): Response<List<Chat>>
}