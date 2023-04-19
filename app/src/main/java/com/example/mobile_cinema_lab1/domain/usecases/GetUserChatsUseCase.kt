package com.example.mobile_cinema_lab1.domain.usecases

import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.datasource.network.models.ChatWithLastMessage
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.IChatRepository
import com.example.mobile_cinema_lab1.repositories.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetUserChatsUseCase {
    private val chatRepository: IChatRepository = ChatRepository()

    operator fun invoke(): Flow<ApiResponse<List<ChatWithLastMessage>>> {
        return chatRepository.getActiveUserChats()
    }

}