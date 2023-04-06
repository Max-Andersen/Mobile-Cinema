package com.example.mobile_cinema_lab1.navigationmodels

import android.os.Parcelable
import com.example.mobile_cinema_lab1.network.models.Episode


@kotlinx.parcelize.Parcelize
data class Episode(
    val episodeId: String,
    val name: String,
    val description: String,
    val director: String,
    val stars: List<String>,
    val year: Int,
    val images: List<String>,
    val runtime: Int,
    val preview: String,
    val filePath: String
) : Parcelable

fun Episode.getNavigationModel() = com.example.mobile_cinema_lab1.navigationmodels.Episode(
    episodeId = this.episodeId,
    name = this.name,
    description = this.description,
    director = this.director,
    stars = this.stars,
    year = this.year,
    images = this.images,
    runtime = this.runtime,
    preview = this.preview,
    filePath = this.filePath
)
