package com.example.mobilecinemalab.domain.usecases.socket

import android.util.Log
import com.example.mobilecinemalab.datasource.network.models.ChatMessage
import com.example.mobilecinemalab.domain.repositoryinterfaces.ISocketRepository
import com.example.mobilecinemalab.repositories.SocketRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow

class GetMessagesFromSocketUseCase {
    private val socketRepository: ISocketRepository = SocketRepository

    operator fun invoke() = flow<ChatMessage> {
        socketRepository.getMessagesFlow().collect {
            try {
                val gson = Gson()
                val data = gson.fromJson(it, ChatMessage::class.java)

                emit(data)
            } catch (e: Exception) {
                Log.d("!", "FAIL TO PARSE!  ${e.message}")
            }
        }
    }
}