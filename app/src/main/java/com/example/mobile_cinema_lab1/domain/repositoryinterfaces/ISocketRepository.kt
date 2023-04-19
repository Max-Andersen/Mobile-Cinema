package com.example.mobile_cinema_lab1.domain.repositoryinterfaces

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.coroutines.CoroutineContext

interface ISocketRepository {
    fun initSocket(chatId: String)

    fun getMessagesFlow(): MutableSharedFlow<String>

    fun sendMessage(message: String)
}