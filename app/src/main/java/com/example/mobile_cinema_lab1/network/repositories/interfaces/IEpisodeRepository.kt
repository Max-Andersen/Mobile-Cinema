package com.example.mobile_cinema_lab1.network.repositories.interfaces

import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Comment
import com.example.mobile_cinema_lab1.network.models.Time
import kotlinx.coroutines.flow.Flow

interface IEpisodeRepository {

    fun getCommentsOnEpisode(episodeId: String): Flow<ApiResponse<List<Comment>>>

    fun addCommentsOnEpisode(episodeId: String, commentText: String): Flow<ApiResponse<Comment>>

    fun getEpisodeTime(episodeId: String): Flow<ApiResponse<Time>>

    fun saveEpisodeTime(episodeId: String, episodeTime: String): Flow<ApiResponse<Nothing>>
}