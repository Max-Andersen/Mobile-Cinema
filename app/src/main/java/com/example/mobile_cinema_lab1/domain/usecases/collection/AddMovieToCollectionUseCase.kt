package com.example.mobile_cinema_lab1.domain.usecases.collection


import com.example.mobile_cinema_lab1.datasource.network.models.MovieId
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.ICollectionRepository
import com.example.mobile_cinema_lab1.repositories.CollectionRepository

class AddMovieToCollectionUseCase(private val collectionId: String, private val movieId: MovieId) {
    private val collectionRepository: ICollectionRepository = CollectionRepository()

    operator fun invoke() = collectionRepository.addMoviesInCollection(collectionId, movieId)

}