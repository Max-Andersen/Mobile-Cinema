package com.example.mobile_cinema_lab1.network.models

@kotlinx.serialization.Serializable
data class LoginResponse(
    val accessToken: String,
    val accessTokenExpiresIn: Int,
    val refreshToken: String,
    val refreshTokenExpiresIn: Int
)
