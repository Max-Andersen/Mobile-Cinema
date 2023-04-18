package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Collection
import com.example.mobile_cinema_lab1.network.models.Name
import com.example.mobile_cinema_lab1.network.repositories.CollectionRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.ICollectionRepository
import kotlinx.coroutines.flow.Flow

class CreateCollectionUseCase(private val newCollectionName: String) {
    private val collectionRepository: ICollectionRepository = CollectionRepository()


    operator fun invoke(): Flow<ApiResponse<Collection>> {
        return collectionRepository.createCollection(Name(name =  newCollectionName))
    }
}