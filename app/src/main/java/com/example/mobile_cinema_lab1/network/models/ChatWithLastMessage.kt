package com.example.mobile_cinema_lab1.network.models

@kotlinx.serialization.Serializable
data class ChatWithLastMessage(
    val chatId: String,
    val chatName: String,
    val lastMessage: ChatMessage
)
