package com.example.mobile_cinema_lab1.additionalmodels

import kotlinx.datetime.LocalDate

data class NotMyMessage(
    val creationDate: LocalDate,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String,
    val text: String,
)
