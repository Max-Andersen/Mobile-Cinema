package com.example.mobile_cinema_lab1.additionalmodels

import kotlinx.datetime.LocalDate

data class MyMessage(
    val creationDate: LocalDate,
    val authorId: String,
    val authorName: String,
    val authorAvatar: String,
    val text: String,
    val isLoading: Boolean,
    val fail: Boolean = false
)
