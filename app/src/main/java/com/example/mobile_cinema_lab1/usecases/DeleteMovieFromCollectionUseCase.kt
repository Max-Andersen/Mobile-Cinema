package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.models.MovieId
import com.example.mobile_cinema_lab1.network.repositories.CollectionRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.ICollectionRepository

class DeleteMovieFromCollectionUseCase(private val collectionId: String, private val movieId: String) {
    private val collectionRepository: ICollectionRepository = CollectionRepository()

    operator fun invoke() = collectionRepository.deleteMovieFromCollection(collectionId, movieId)

}