package com.example.mobile_cinema_lab1.navigationmodels

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Collection(
    val collectionId: String,
    val collectionName: String,
) : Parcelable


fun com.example.mobile_cinema_lab1.datasource.network.models.Collection.getNavigationModel() = Collection(
    collectionId = collectionId,
    collectionName = name
)