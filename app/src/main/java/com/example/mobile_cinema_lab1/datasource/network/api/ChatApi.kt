package com.example.mobile_cinema_lab1.datasource.network.api


import com.example.mobile_cinema_lab1.datasource.network.models.ChatWithLastMessage
import retrofit2.Response
import retrofit2.http.GET

interface ChatApi {

    @GET("chats")
    suspend fun getUserChats(): Response<List<ChatWithLastMessage>>

}