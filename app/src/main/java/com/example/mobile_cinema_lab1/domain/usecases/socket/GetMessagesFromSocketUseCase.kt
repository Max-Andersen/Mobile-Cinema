package com.example.mobile_cinema_lab1.domain.usecases.socket

import android.util.Log
import com.example.mobile_cinema_lab1.datasource.network.models.ChatMessage
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.ISocketRepository
import com.example.mobile_cinema_lab1.repositories.SocketRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow

class GetMessagesFromSocketUseCase {
    private val socketRepository: ISocketRepository = SocketRepository

    operator fun invoke() = flow<ChatMessage> {
        socketRepository.getMessagesFlow().collect {
            try {
                val gson = Gson()
                val data = gson.fromJson(it, ChatMessage::class.java)
                Log.d("!", data.toString())

                emit(data)
            } catch (e: Exception) {
                Log.d("!", "FAIL TO PARSE!  ${e.message}")
            }
        }
    }
}