package com.example.mobilecinemalab.ui.collections

data class CollectionUIModel(
    val collectionId: String,
    val name: String,
    var iconId: String
)

fun CollectionUIModel.getNavigationModel() = com.example.mobilecinemalab.navigationmodels.Collection(collectionId = collectionId, collectionName = name)