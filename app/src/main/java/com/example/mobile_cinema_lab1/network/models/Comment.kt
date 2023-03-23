package com.example.mobile_cinema_lab1.network.models

import kotlinx.datetime.LocalDate

@kotlinx.serialization.Serializable
data class Comment(
    val commentId: String,
    val creationDateTime: LocalDate,
    val authorName: String,
    val authorAvatar: String,
    val text: String
)
