package com.example.mobilecinemalab.domain.repositoryinterfaces

import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.Comment
import com.example.mobilecinemalab.datasource.network.models.Time
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface IEpisodeRepository {

    fun getCommentsOnEpisode(episodeId: String): Flow<ApiResponse<List<Comment>>>

    fun addCommentsOnEpisode(episodeId: String, commentText: String): Flow<ApiResponse<Comment>>

    fun getEpisodeTime(episodeId: String): Flow<ApiResponse<Time>>

    fun saveEpisodeTime(episodeId: String, episodeTime: Int): Flow<ApiResponse<ResponseBody>>
}