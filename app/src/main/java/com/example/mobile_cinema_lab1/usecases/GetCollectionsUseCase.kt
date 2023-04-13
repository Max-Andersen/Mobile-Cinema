package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Collection
import com.example.mobile_cinema_lab1.network.repositories.CollectionRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.ICollectionRepository
import kotlinx.coroutines.flow.Flow

class GetCollectionsUseCase {
    private val collectionRepository: ICollectionRepository = CollectionRepository()

    operator fun invoke(): Flow<ApiResponse<List<Collection>>> {
        return collectionRepository.getUserCollection()
    }
}