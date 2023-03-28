package com.example.mobile_cinema_lab1.network.models

@kotlinx.serialization.Serializable
data class Movie(
    val movieId: String,
    val name: String,
    val description: String,
    val age: String,
    val chatInfo: List<Chat>,
    val images: List<String>,
    val poster: String,
    val tags: List<Tags>
)
