package com.example.mobilecinemalab.repositories

import com.example.mobilecinemalab.datasource.network.Network
import com.example.mobilecinemalab.datasource.network.api.CollectionsApi
import com.example.mobilecinemalab.datasource.network.models.MovieId
import com.example.mobilecinemalab.datasource.network.models.Name
import com.example.mobilecinemalab.domain.repositoryinterfaces.ICollectionRepository

class CollectionRepository: ICollectionRepository, BaseRepository() {
    private val collectionsApi: CollectionsApi = Network.getCollectionsApi()

    override fun getUserCollection() = apiRequestFlow { collectionsApi.getCollections() }

    override fun createCollection(collectionName: Name) =
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