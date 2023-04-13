package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.models.MovieId
import com.example.mobile_cinema_lab1.network.repositories.CollectionRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.ICollectionRepository

class AddMovieToCollectionUseCase(private val collectionId: String, private val movieId: MovieId) {
    private val collectionRepository: ICollectionRepository = CollectionRepository()

    operator fun invoke() = collectionRepository.addMoviesInCollection(collectionId, movieId)

}