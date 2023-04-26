package com.example.mobilecinemalab.domain.usecases.collection

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.domain.repositoryinterfaces.ICollectionRepository
import com.example.mobilecinemalab.repositories.CollectionRepository
import com.example.mobilecinemalab.datasource.network.models.Collection
import com.example.mobilecinemalab.datasource.network.models.Name
import kotlinx.coroutines.flow.Flow

class CreateCollectionUseCase(private val newCollectionName: String) {
    private val collectionRepository: ICollectionRepository = CollectionRepository()


    operator fun invoke(): Flow<ApiResponse<Collection>> {
        return collectionRepository.createCollection(Name(name =  newCollectionName))
    }
}