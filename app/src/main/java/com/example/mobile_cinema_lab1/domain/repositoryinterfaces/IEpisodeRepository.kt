package com.example.mobile_cinema_lab1.domain.repositoryinterfaces

import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.datasource.network.models.Comment
import com.example.mobile_cinema_lab1.datasource.network.models.Time
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface IEpisodeRepository {

    fun getCommentsOnEpisode(episodeId: String): Flow<ApiResponse<List<Comment>>>

    fun addCommentsOnEpisode(episodeId: String, commentText: String): Flow<ApiResponse<Comment>>

    fun getEpisodeTime(episodeId: String): Flow<ApiResponse<Time>>

    fun saveEpisodeTime(episodeId: String, episodeTime: Int): Flow<ApiResponse<ResponseBody>>
}