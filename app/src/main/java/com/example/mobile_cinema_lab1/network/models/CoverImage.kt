package com.example.mobile_cinema_lab1.network.models

@kotlinx.serialization.Serializable
data class CoverImage(
    val backgroundImage: String,
    val foregroundImage: String
)
