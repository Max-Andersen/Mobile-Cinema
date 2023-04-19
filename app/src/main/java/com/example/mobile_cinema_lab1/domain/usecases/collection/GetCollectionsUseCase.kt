package com.example.mobile_cinema_lab1.domain.usecases.collection

import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.datasource.network.models.Collection
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.ICollectionRepository
import com.example.mobile_cinema_lab1.repositories.CollectionRepository
import kotlinx.coroutines.flow.Flow

class GetCollectionsUseCase {
    private val collectionRepository: ICollectionRepository = CollectionRepository()

    operator fun invoke(): Flow<ApiResponse<List<Collection>>> {
        return collectionRepository.getUserCollection()
    }
}