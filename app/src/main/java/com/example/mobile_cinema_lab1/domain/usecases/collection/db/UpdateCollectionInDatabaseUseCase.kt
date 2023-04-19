package com.example.mobile_cinema_lab1.domain.usecases.collection.db

import com.example.mobile_cinema_lab1.datasource.database.Database

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