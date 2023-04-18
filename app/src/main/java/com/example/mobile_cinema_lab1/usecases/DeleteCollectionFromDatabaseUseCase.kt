package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.database.Database

class DeleteCollectionFromDatabaseUseCase(private val collectionId: String) {
    private val databaseDao = Database.getDao()

    operator fun invoke(){
        databaseDao.deleteCollection(collectionId)
    }
}