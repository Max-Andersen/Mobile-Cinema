package com.example.mobile_cinema_lab1.domain.usecases.collection.db

import com.example.mobile_cinema_lab1.datasource.database.CollectionDBModel
import com.example.mobile_cinema_lab1.datasource.database.DatabaseRepository
import com.example.mobile_cinema_lab1.datasource.database.IDatabaseRepository

class InsertCollectionToDatabaseUseCase(
    private val collectionId: String,
    private val collectionName: String,
    private val iconId: String
) {
    private val databaseRepository: IDatabaseRepository = DatabaseRepository()

    operator fun invoke() = databaseRepository.insertCollection(CollectionDBModel(collectionId, collectionName, iconId))
}