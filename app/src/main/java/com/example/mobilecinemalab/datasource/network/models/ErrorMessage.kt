package com.example.mobilecinemalab.datasource.network.models

@kotlinx.serialization.Serializable
data class ErrorMessage(
    val code: String,
    val message: String?
)
