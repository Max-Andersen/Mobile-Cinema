package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.database.Database

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