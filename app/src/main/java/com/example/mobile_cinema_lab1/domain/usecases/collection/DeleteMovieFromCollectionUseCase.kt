package com.example.mobile_cinema_lab1.domain.usecases.collection

import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.ICollectionRepository
import com.example.mobile_cinema_lab1.repositories.CollectionRepository

class DeleteMovieFromCollectionUseCase(private val collectionId: String, private val movieId: String) {
    private val collectionRepository: ICollectionRepository = CollectionRepository()

    operator fun invoke() = collectionRepository.deleteMovieFromCollection(collectionId, movieId)

}