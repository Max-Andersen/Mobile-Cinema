package com.example.mobilecinemalab.domain.repositoryinterfaces

import kotlinx.coroutines.flow.MutableSharedFlow

interface ISocketRepository {
    fun initSocket(chatId: String)

    fun getMessagesFlow(): MutableSharedFlow<String>

    fun sendMessage(message: String)

    fun closeSocket()
}