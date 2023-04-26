package com.example.mobilecinemalab.domain.usecases.collection.db

import com.example.mobilecinemalab.datasource.database.DatabaseRepository
import com.example.mobilecinemalab.datasource.database.IDatabaseRepository

class GetCollectionFromDatabaseUseCase(private val collectionId: String) {
    private val databaseRepository: IDatabaseRepository = DatabaseRepository()

    operator fun invoke() = databaseRepository.getCollection(collectionId)
}