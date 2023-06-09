package com.example.mobilecinemalab.datasource.network.models

@kotlinx.serialization.Serializable
data class ChatWithLastMessage(
    val chatId: String,
    val chatName: String,
    val lastMessage: ChatMessage
)
