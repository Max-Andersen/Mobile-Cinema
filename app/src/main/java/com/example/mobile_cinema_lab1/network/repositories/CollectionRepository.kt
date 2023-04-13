package com.example.mobile_cinema_lab1.network.repositories

import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.network.api.CollectionsApi
import com.example.mobile_cinema_lab1.network.models.MovieId
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.ICollectionRepository

class CollectionRepository: ICollectionRepository, BaseRepository() {
    private val collectionsApi: CollectionsApi = Network.getCollectionsApi()

    override fun getUserCollection() = apiRequestFlow { collectionsApi.getCollections() }

    override fun createCollection(collectionName: String) =
        apiRequestFlow { collectionsApi.createCollection(collectionName) }

    override fun deleteCollection(collectionId: String) =
        apiRequestFlow { collectionsApi.deleteCollection(collectionId) }

    override fun getMoviesInCollection(collectionId: String) =
        apiRequestFlow { collectionsApi.getMoviesInCollection(collectionId) }

    override fun addMoviesInCollection(collectionId: String, movieId: MovieId) =
        apiRequestFlow { collectionsApi.addMovieInCollection(collectionId, movieId) }

    override fun deleteMovieFromCollection(collectionId: String, movieId: String) =
        apiRequestFlow { collectionsApi.deleteMovieFromCollection(collectionId, movieId) }
}