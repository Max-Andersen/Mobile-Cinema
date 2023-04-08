package com.example.mobile_cinema_lab1.additionalmodels

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class NotMyMessage(
    val creationDate: LocalDateTime,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String?,
    var showAvatar: Boolean = true,
    val text: String,
)
