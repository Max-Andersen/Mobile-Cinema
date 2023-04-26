package com.example.mobilecinemalab.datasource.network.models

@kotlinx.serialization.Serializable
data class UpdateUserInfo(
    val firstName: String?,
    val lastName: String?,
    val password: String?
)
