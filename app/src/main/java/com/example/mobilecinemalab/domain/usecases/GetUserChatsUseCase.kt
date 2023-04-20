package com.example.mobilecinemalab.domain.usecases

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.ChatWithLastMessage
import com.example.mobilecinemalab.domain.repositoryinterfaces.IChatRepository
import com.example.mobilecinemalab.repositories.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetUserChatsUseCase {
    private val chatRepository: IChatRepository = ChatRepository()

    operator fun invoke(): Flow<ApiResponse<List<ChatWithLastMessage>>> {
        return chatRepository.getActiveUserChats()
    }

}