package com.example.mobilecinemalab.datasource.network.models

@kotlinx.serialization.Serializable
data class Collection(
    val collectionId: String,
    val name: String
)