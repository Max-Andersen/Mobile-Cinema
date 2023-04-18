package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.database.DatabaseRepository
import com.example.mobile_cinema_lab1.database.IDatabaseRepository

class GetCollectionFromDatabaseUseCase(private val collectionId: String) {
    private val databaseRepository: IDatabaseRepository = DatabaseRepository()


    operator fun invoke() = databaseRepository.getCollection(collectionId)
}