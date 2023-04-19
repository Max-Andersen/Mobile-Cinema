package com.example.mobile_cinema_lab1.domain.usecases

import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.ICollectionRepository
import com.example.mobile_cinema_lab1.repositories.CollectionRepository
import com.example.mobile_cinema_lab1.datasource.network.models.Collection
import com.example.mobile_cinema_lab1.datasource.network.models.Name
import kotlinx.coroutines.flow.Flow

class CreateCollectionUseCase(private val newCollectionName: String) {
    private val collectionRepository: ICollectionRepository = CollectionRepository()


    operator fun invoke(): Flow<ApiResponse<Collection>> {
        return collectionRepository.createCollection(Name(name =  newCollectionName))
    }
}