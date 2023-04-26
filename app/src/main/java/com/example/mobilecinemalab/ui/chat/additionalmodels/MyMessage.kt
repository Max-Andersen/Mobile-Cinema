package com.example.mobilecinemalab.ui.chat.additionalmodels

import kotlinx.datetime.LocalDateTime

data class MyMessage(
    var creationDate: LocalDateTime,
    val authorId: String,
    var authorName: String,
    var authorAvatar: String?,
    val text: String,
    var showAvatar: Boolean = true,
    var isLoading: Boolean,
    var fail: Boolean = false
)
