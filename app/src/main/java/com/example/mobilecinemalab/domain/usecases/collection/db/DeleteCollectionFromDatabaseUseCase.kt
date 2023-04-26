package com.example.mobilecinemalab.domain.usecases.collection.db

import com.example.mobilecinemalab.datasource.database.Database

class DeleteCollectionFromDatabaseUseCase(private val collectionId: String) {
    private val databaseDao = Database.getDao()

    operator fun invoke(){
        databaseDao.deleteCollection(collectionId)
    }
}