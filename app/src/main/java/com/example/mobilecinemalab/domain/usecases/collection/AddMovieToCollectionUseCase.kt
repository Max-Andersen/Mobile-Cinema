package com.example.mobilecinemalab.domain.usecases.collection


import com.example.mobilecinemalab.datasource.network.models.MovieId
import com.example.mobilecinemalab.domain.repositoryinterfaces.ICollectionRepository
import com.example.mobilecinemalab.repositories.CollectionRepository

class AddMovieToCollectionUseCase(private val collectionId: String, private val movieId: MovieId) {
    private val collectionRepository: ICollectionRepository = CollectionRepository()

    operator fun invoke() = collectionRepository.addMoviesInCollection(collectionId, movieId)

}