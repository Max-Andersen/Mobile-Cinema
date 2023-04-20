package com.example.mobilecinemalab.datasource.database

interface IDatabaseRepository {
    fun getCollection(collectionId: String): CollectionDBModel?

    fun insertCollection(collection: CollectionDBModel)

    fun deleteCollection(collectionId: String)

    fun updateCollectionIcon(collectionId: String, iconId: String)

    fun updateCollectionName(collectionId: String, newName: String)
}