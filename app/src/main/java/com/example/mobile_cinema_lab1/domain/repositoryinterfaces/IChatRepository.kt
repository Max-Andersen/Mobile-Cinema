package com.example.mobile_cinema_lab1.domain.repositoryinterfaces

import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.datasource.network.models.ChatWithLastMessage
import kotlinx.coroutines.flow.Flow

interface IChatRepository {
    fun getActiveUserChats(): Flow<ApiResponse<List<ChatWithLastMessage>>>
}