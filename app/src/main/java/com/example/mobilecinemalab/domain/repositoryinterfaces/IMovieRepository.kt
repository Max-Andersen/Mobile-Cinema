package com.example.mobilecinemalab.domain.repositoryinterfaces

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.CoverImage
import com.example.mobilecinemalab.datasource.network.models.Episode
import com.example.mobilecinemalab.datasource.network.models.EpisodeShort
import com.example.mobilecinemalab.datasource.network.models.Movie
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface IMovieRepository {

    fun getCoverOnPromotedFilm(): Flow<ApiResponse<CoverImage>>

    fun getMoviesByFilter(filter: String): Flow<ApiResponse<List<Movie>>>

    fun getEpisodeListOfMovie(movieId: String): Flow<ApiResponse<List<Episode>>>

    fun removeMovieFromCompilation(movieId: String): Flow<ApiResponse<ResponseBody>>

    fun getUserHistory(): Flow<ApiResponse<List<EpisodeShort>>>
}