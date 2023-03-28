package com.example.mobile_cinema_lab1.usecases.repositoryinterfaces

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.CoverResponse
import com.example.mobile_cinema_lab1.network.models.Episode
import com.example.mobile_cinema_lab1.network.models.EpisodeShort
import com.example.mobile_cinema_lab1.network.models.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getCoverOnPromotedFilm(): Flow<ApiResponse<CoverResponse>>

    fun getMoviesByFilter(filter: String): Flow<ApiResponse<List<Movie>>>

    fun getEpisodeListOfMovie(movieId: String): Flow<ApiResponse<List<Episode>>>

    fun removeMovieFromCompilation(movieId: String): Flow<ApiResponse<Nothing>>

    fun getUserHistory(): Flow<ApiResponse<List<EpisodeShort>>>
}