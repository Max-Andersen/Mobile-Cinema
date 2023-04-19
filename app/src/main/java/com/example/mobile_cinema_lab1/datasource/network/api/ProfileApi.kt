package com.example.mobile_cinema_lab1.datasource.network.api


import com.example.mobile_cinema_lab1.datasource.network.models.Chat
import com.example.mobile_cinema_lab1.datasource.network.models.UpdateUserInfo
import com.example.mobile_cinema_lab1.datasource.network.models.UserInfo
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProfileApi {

    @GET("profile")
    suspend fun getUserInfo(): Response<UserInfo>

    @PATCH("profile")
    suspend fun updateUserInfo(@Body newData: UpdateUserInfo): Response<UserInfo>

    @POST("profile/avatar")
    suspend fun uploadProfileImage(@Part part: MultipartBody): Response<Nothing>

    @GET("chats")
    suspend fun getUserChats(): Response<List<Chat>>
}