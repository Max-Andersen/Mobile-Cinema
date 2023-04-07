package com.example.mobile_cinema_lab1.network.models

import kotlinx.datetime.LocalDateTime

data class ChatMessage(
    val messageId: String,
    val creationDateTime: String,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String,
    val text: String
)
