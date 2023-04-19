package com.example.mobile_cinema_lab1.datasource.network.models

@kotlinx.serialization.Serializable
data class UpdateUserInfo(
    val firstName: String?,
    val lastName: String?,
    val password: String?
)
