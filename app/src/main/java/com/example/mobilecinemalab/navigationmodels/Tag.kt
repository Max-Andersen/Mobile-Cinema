package com.example.mobilecinemalab.navigationmodels

import android.os.Parcelable

@kotlinx.parcelize.Parcelize

data class Tag(
    val tagId: String,
    val tagName: String,
    val categoryName: String
) : Parcelable


fun com.example.mobilecinemalab.datasource.network.models.Tag.getNavigationModel() = Tag(
    tagId = this.tagId,
    tagName = this.tagName,
    categoryName = this.categoryName
)