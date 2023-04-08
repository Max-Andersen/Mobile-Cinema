package com.example.mobile_cinema_lab1.network.models

@kotlinx.serialization.Serializable
data class UserInfo(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String?
)
