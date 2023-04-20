package com.example.mobilecinemalab.domain.usecases.collection

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.Collection
import com.example.mobilecinemalab.domain.repositoryinterfaces.ICollectionRepository
import com.example.mobilecinemalab.repositories.CollectionRepository
import kotlinx.coroutines.flow.Flow

class GetCollectionsUseCase {
    private val collectionRepository: ICollectionRepository = CollectionRepository()

    operator fun invoke(): Flow<ApiResponse<List<Collection>>> {
        return collectionRepository.getUserCollection()
    }
}