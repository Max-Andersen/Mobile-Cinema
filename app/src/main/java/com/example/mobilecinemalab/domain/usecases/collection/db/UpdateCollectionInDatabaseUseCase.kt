package com.example.mobilecinemalab.domain.usecases.collection.db

import com.example.mobilecinemalab.datasource.database.Database

class UpdateCollectionInDatabaseUseCase(
    private val collectionId: String,
    private val collectionName: String,
    private val collectionIconId: String
) {
    private val databaseDao = Database.getDao()

    operator fun invoke(){
        databaseDao.updateCollectionIcon(collectionId, collectionIconId)
        databaseDao.updateCollectionName(collectionId, collectionName)
    }

}