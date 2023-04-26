package com.example.mobilecinemalab.domain.usecases.collection

import com.example.mobilecinemalab.domain.repositoryinterfaces.ICollectionRepository
import com.example.mobilecinemalab.repositories.CollectionRepository

class GetMoviesInCollectionUseCase(private val collectionId: String) {
    private val collectionRepository: ICollectionRepository = CollectionRepository()

    operator fun invoke() = collectionRepository.getMoviesInCollection(collectionId)

}