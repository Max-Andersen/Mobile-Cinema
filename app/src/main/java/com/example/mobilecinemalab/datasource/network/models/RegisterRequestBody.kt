package com.example.mobilecinemalab.datasource.network.models

@kotlinx.serialization.Serializable
data class RegisterRequestBody(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)
