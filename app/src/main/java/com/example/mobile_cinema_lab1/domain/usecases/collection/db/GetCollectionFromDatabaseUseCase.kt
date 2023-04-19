package com.example.mobile_cinema_lab1.domain.usecases.collection.db

import com.example.mobile_cinema_lab1.datasource.database.DatabaseRepository
import com.example.mobile_cinema_lab1.datasource.database.IDatabaseRepository

class GetCollectionFromDatabaseUseCase(private val collectionId: String) {
    private val databaseRepository: IDatabaseRepository = DatabaseRepository()


    operator fun invoke() = databaseRepository.getCollection(collectionId)
}