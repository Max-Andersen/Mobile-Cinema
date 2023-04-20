package com.example.mobilecinemalab.datasource.network.models

import kotlinx.datetime.LocalDate

@kotlinx.serialization.Serializable
data class Comment(
    val commentId: String,
    val creationDateTime: LocalDate,
    val authorName: String,
    val authorAvatar: String,
    val text: String
)
