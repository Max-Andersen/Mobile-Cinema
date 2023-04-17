package com.example.mobile_cinema_lab1.network.models

@kotlinx.serialization.Serializable
data class ChatMessage(
    val messageId: String,
    val creationDateTime: String,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String,
    val text: String
)
