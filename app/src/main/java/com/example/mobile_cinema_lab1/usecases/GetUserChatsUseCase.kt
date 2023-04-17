package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.ChatWithLastMessage
import com.example.mobile_cinema_lab1.network.repositories.ChatRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IChatRepository
import kotlinx.coroutines.flow.Flow

class GetUserChatsUseCase {
    private val chatRepository: IChatRepository = ChatRepository()

    operator fun invoke(): Flow<ApiResponse<List<ChatWithLastMessage>>> {
        return chatRepository.getActiveUserChats()
    }

}