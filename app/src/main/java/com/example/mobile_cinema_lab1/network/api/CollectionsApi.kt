package com.example.mobile_cinema_lab1.network.api

import com.example.mobile_cinema_lab1.network.models.Collection
import com.example.mobile_cinema_lab1.network.models.Movie
import com.example.mobile_cinema_lab1.network.models.MovieId
import retrofit2.Response
import retrofit2.http.*

interface CollectionsApi {

    @GET("collections")
    suspend fun getCollections(): Response<List<Collection>>

    @POST("collections")
    suspend fun createCollection(@Body collectionName: String): Response<Collection>

    @DELETE("collections/{collectionId}")
    suspend fun deleteCollection(@Path("collectionId") collectionId: String): Response<Void>

    @GET("collections/{collectionId}/movies")
    suspend fun getMoviesInCollection(@Path("collectionId") collectionId: String): Response<List<Movie>>

    @POST("collections/{collectionId}/movies")
    suspend fun addMovieInCollection(
        @Path("collectionId") collectionId: String,
        @Body movieId: MovieId
    ): Response<Void>

    @DELETE("collections/{collectionId}/movies")
    suspend fun deleteMovieFromCollection(
        @Path("collectionId") collectionId: String,
        @Query("movieId") movieId: String
    ): Response<Void>
}