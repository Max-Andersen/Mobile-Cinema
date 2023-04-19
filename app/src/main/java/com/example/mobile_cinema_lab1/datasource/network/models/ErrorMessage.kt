package com.example.mobile_cinema_lab1.datasource.network.models

@kotlinx.serialization.Serializable
data class ErrorMessage(
    val code: String,
    val message: String?
)
