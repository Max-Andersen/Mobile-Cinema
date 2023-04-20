package com.example.mobilecinemalab.domain.usecases.collection

import com.example.mobilecinemalab.domain.repositoryinterfaces.ICollectionRepository
import com.example.mobilecinemalab.repositories.CollectionRepository

class DeleteMovieFromCollectionUseCase(private val collectionId: String, private val movieId: String) {
    private val collectionRepository: ICollectionRepository = CollectionRepository()

    operator fun invoke() = collectionRepository.deleteMovieFromCollection(collectionId, movieId)

}