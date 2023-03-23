package com.example.mobile_cinema_lab1.network.models

@kotlinx.serialization.Serializable
data class LoginRequestBody(
    val email: String,
    val password: String
)
