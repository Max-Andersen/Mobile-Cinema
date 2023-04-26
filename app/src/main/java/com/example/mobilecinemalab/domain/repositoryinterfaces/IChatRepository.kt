package com.example.mobilecinemalab.domain.repositoryinterfaces

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.ChatWithLastMessage
import kotlinx.coroutines.flow.Flow

interface IChatRepository {
    fun getActiveUserChats(): Flow<ApiResponse<List<ChatWithLastMessage>>>
}