package com.example.mobile_cinema_lab1.usecases.repositoryinterfaces

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Collection
import com.example.mobile_cinema_lab1.network.models.Movie
import com.example.mobile_cinema_lab1.network.models.MovieId
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface ICollectionRepository {
    fun getUserCollection(): Flow<ApiResponse<List<Collection>>>

    fun createCollection(collectionName: String): Flow<ApiResponse<Collection>>

    fun deleteCollection(collectionId: String): Flow<ApiResponse<Void>>

    fun getMoviesInCollection(collectionId: String): Flow<ApiResponse<List<Movie>>>

    fun addMoviesInCollection(collectionId: String, movieId: MovieId): Flow<ApiResponse<ResponseBody>>

    fun deleteMovieFromCollection(collectionId: String, movieId: String): Flow<ApiResponse<ResponseBody>>
}