package com.example.mobile_cinema_lab1.navigationmodels

import android.os.Parcelable
import com.example.mobile_cinema_lab1.network.models.Movie

@kotlinx.parcelize.Parcelize
data class Movie(
    val movieId: String,
    val name: String,
    val description: String,
    val age: String,
    val chatInfo: Chat,
    val imageUrls: List<String>,
    val poster: String,
    val tags: List<Tag>
) : Parcelable


fun Movie.getNavigationModel() =
    com.example.mobile_cinema_lab1.navigationmodels.Movie(
        movieId = this.movieId,
        name = this.name,
        description = this.description,
        age = this.age,
        chatInfo = this.chatInfo.getNavigationModel(),
        imageUrls = this.imageUrls,
        poster = this.poster,
        tags = this.tags.map { tag -> tag.getNavigationModel() }
    )
