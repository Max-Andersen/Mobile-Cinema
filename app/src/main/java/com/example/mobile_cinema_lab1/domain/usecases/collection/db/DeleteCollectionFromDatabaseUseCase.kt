package com.example.mobile_cinema_lab1.domain.usecases.collection.db

import com.example.mobile_cinema_lab1.datasource.database.Database

class DeleteCollectionFromDatabaseUseCase(private val collectionId: String) {
    private val databaseDao = Database.getDao()

    operator fun invoke(){
        databaseDao.deleteCollection(collectionId)
    }
}