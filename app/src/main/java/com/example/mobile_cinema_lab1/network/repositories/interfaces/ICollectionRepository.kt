package com.example.mobile_cinema_lab1.network.repositories.interfaces

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Collection
import com.example.mobile_cinema_lab1.network.models.Movie
import kotlinx.coroutines.flow.Flow

interface ICollectionRepository {
    fun getUserCollection(): Flow<ApiResponse<List<Collection>>>

    fun createCollection(collectionName: String): Flow<ApiResponse<Collection>>

    fun deleteCollection(collectionId: String): Flow<ApiResponse<Nothing>>

    fun getMoviesInCollection(collectionId: String): Flow<ApiResponse<List<Movie>>>

    fun addMoviesInCollection(collectionId: String, movieId: String): Flow<ApiResponse<Nothing>>

    fun deleteMovieFromCollection(collectionId: String, movieId: String): Flow<ApiResponse<Nothing>>
}