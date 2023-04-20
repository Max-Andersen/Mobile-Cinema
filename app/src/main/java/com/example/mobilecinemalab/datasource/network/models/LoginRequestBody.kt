package com.example.mobilecinemalab.datasource.network.models

@kotlinx.serialization.Serializable
data class LoginRequestBody(
    val email: String,
    val password: String
)
