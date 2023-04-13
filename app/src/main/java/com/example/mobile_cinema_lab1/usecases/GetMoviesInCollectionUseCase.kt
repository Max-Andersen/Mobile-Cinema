package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.repositories.CollectionRepository
import com.example.mobile_cinema_lab1.network.repositories.MovieRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.ICollectionRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IMovieRepository

class GetMoviesInCollectionUseCase(private val collectionId: String) {
    private val collectionRepository: ICollectionRepository = CollectionRepository()

    operator fun invoke() = collectionRepository.getMoviesInCollection(collectionId)

}