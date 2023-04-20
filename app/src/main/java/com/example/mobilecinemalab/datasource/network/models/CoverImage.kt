package com.example.mobilecinemalab.datasource.network.models

@kotlinx.serialization.Serializable
data class CoverImage(
    val backgroundImage: String,
    val foregroundImage: String
)
