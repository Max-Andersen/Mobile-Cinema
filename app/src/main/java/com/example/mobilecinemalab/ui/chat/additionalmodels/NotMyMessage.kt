package com.example.mobilecinemalab.ui.chat.additionalmodels

import kotlinx.datetime.LocalDateTime

data class NotMyMessage(
    val creationDate: LocalDateTime,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String?,
    var showAvatar: Boolean = true,
    val text: String,
)
