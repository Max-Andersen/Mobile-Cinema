package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.database.CollectionDBModel
import com.example.mobile_cinema_lab1.database.DatabaseRepository
import com.example.mobile_cinema_lab1.database.IDatabaseRepository

class InsertCollectionToDatabaseUseCase(
    private val collectionId: String,
    private val collectionName: String,
    private val iconId: String
) {
    private val databaseRepository: IDatabaseRepository = DatabaseRepository()

    operator fun invoke() = databaseRepository.insertCollection(CollectionDBModel(collectionId, collectionName, iconId))
}