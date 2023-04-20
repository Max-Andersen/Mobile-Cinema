package com.example.mobilecinemalab.domain.repositoryinterfaces

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.Name
import com.example.mobilecinemalab.datasource.network.models.Collection
import com.example.mobilecinemalab.datasource.network.models.Movie
import com.example.mobilecinemalab.datasource.network.models.MovieId
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface ICollectionRepository {
    fun getUserCollection(): Flow<ApiResponse<List<Collection>>>

    fun createCollection(collectionName: Name): Flow<ApiResponse<Collection>>

    fun deleteCollection(collectionId: String): Flow<ApiResponse<Void>>

    fun getMoviesInCollection(collectionId: String): Flow<ApiResponse<List<Movie>>>

    fun addMoviesInCollection(collectionId: String, movieId: MovieId): Flow<ApiResponse<ResponseBody>>

    fun deleteMovieFromCollection(collectionId: String, movieId: String): Flow<ApiResponse<ResponseBody>>
}