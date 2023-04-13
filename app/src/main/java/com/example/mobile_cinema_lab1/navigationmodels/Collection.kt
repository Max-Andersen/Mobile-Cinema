package com.example.mobile_cinema_lab1.navigationmodels

import android.os.Parcelable
import com.example.mobile_cinema_lab1.network.models.Collection

@kotlinx.parcelize.Parcelize
data class Collection(
    val collectionId: String,
    val collectionName: String
) : Parcelable


fun Collection.getNavigationModel() = com.example.mobile_cinema_lab1.navigationmodels.Collection(
    collectionId = collectionId,
    collectionName = name
)