package com.example.mobile_cinema_lab1.domain.repositoryinterfaces

import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.datasource.network.models.CoverImage
import com.example.mobile_cinema_lab1.datasource.network.models.Episode
import com.example.mobile_cinema_lab1.datasource.network.models.EpisodeShort
import com.example.mobile_cinema_lab1.datasource.network.models.Movie
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface IMovieRepository {

    fun getCoverOnPromotedFilm(): Flow<ApiResponse<CoverImage>>

    fun getMoviesByFilter(filter: String): Flow<ApiResponse<List<Movie>>>

    fun getEpisodeListOfMovie(movieId: String): Flow<ApiResponse<List<Episode>>>

    fun removeMovieFromCompilation(movieId: String): Flow<ApiResponse<ResponseBody>>

    fun getUserHistory(): Flow<ApiResponse<List<EpisodeShort>>>
}