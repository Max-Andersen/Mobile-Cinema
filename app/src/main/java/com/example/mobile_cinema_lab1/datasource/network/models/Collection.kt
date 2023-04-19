package com.example.mobile_cinema_lab1.datasource.network.models

@kotlinx.serialization.Serializable
data class Collection(
    val collectionId: String,
    val name: String
)