package com.example.mobile_cinema_lab1.ui.collections

data class CollectionUIModel(
    val collectionId: String,
    val name: String,
    var iconId: String
)

fun CollectionUIModel.getNavigationModel() = com.example.mobile_cinema_lab1.navigationmodels.Collection(collectionId = collectionId, collectionName = name)