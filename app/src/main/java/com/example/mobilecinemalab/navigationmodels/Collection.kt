package com.example.mobilecinemalab.navigationmodels

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Collection(
    val collectionId: String,
    val collectionName: String,
) : Parcelable


fun com.example.mobilecinemalab.datasource.network.models.Collection.getNavigationModel() = Collection(
    collectionId = collectionId,
    collectionName = name
)