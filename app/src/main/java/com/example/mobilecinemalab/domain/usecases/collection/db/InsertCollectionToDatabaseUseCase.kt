package com.example.mobilecinemalab.domain.usecases.collection.db

import com.example.mobilecinemalab.datasource.database.CollectionDBModel
import com.example.mobilecinemalab.datasource.database.DatabaseRepository
import com.example.mobilecinemalab.datasource.database.IDatabaseRepository

class InsertCollectionToDatabaseUseCase(
    private val collectionId: String,
    private val collectionName: String,
    private val iconId: String
) {
    private val databaseRepository: IDatabaseRepository = DatabaseRepository()

    operator fun invoke() = databaseRepository.insertCollection(CollectionDBModel(collectionId, collectionName, iconId))
}