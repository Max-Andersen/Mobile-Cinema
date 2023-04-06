package com.example.mobile_cinema_lab1.navigationmodels

import android.os.Parcelable
import com.example.mobile_cinema_lab1.network.models.Tag

@kotlinx.parcelize.Parcelize

data class Tag(
    val tagId: String,
    val tagName: String,
    val categoryName: String
) : Parcelable


fun Tag.getNavigationModel() = com.example.mobile_cinema_lab1.navigationmodels.Tag(
    tagId = this.tagId,
    tagName = this.tagName,
    categoryName = this.categoryName
)