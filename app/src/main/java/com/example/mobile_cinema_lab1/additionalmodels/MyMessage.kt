package com.example.mobile_cinema_lab1.additionalmodels

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class MyMessage(
    val creationDate: LocalDateTime,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String?,
    val text: String,
    var showAvatar: Boolean = true,
    var isLoading: Boolean,
    var fail: Boolean = false
)
