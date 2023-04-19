package com.example.mobile_cinema_lab1.datasource.network.models

@kotlinx.serialization.Serializable
data class EpisodeShort(
    val episodeId: String,
    val movieId: String,
    val episodeName: String,
    val movieName: String,
    val preview: String,
    val filePath: String,
    val time: String
)
