package com.example.mobile_cinema_lab1.usecases.repositoryinterfaces

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.ChatWithLastMessage
import kotlinx.coroutines.flow.Flow

interface IChatRepository {
    fun getActiveUserChats(): Flow<ApiResponse<List<ChatWithLastMessage>>>
}