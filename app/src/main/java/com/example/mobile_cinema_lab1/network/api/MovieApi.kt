package com.example.mobile_cinema_lab1.network.api

import com.example.mobile_cinema_lab1.network.models.Episode
import com.example.mobile_cinema_lab1.network.models.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movies")
    suspend fun getMovies(@Query("filter") filter: String): Response<List<Movie>> // new, inTrend, forMe, lastView, compilation

    @GET("movies/{movieId}/episodes")
    suspend fun getEpisodesFromMovie(@Path("movieId") movieId: String): Response<List<Episode>>

    @POST("movies/{movieId}/episodes")
    suspend fun removeFilmFromCompilation(@Path("movieId") movieId: String): Response<Nothing>
}