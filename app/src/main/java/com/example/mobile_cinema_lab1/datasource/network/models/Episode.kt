package com.example.mobile_cinema_lab1.datasource.network.models

@kotlinx.serialization.Serializable
data class Episode(
    val episodeId : String,
    val name : String,
    val description : String,
    val director : String,
    val stars : List<String>,
    val year : Int,
    val images : List<String>,
    val runtime : Int,
    val preview : String,
    val filePath : String
)
