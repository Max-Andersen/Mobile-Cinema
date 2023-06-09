package com.example.mobilecinemalab.datasource.network.api


import com.example.mobilecinemalab.datasource.network.models.Comment
import com.example.mobilecinemalab.datasource.network.models.Time
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EpisodesApi {

    @GET("episodes/{episodeId}/comments")
    suspend fun getCommentsOnEpisode(@Path("episodeId") episodeId: String): Response<List<Comment>>

    @POST("episodes/{episodeId}/comments")
    suspend fun addCommentsOnEpisode(@Path("episodeId") episodeId: String, @Body commentText: String): Response<Comment>

    @GET("episodes/{episodeId}/time")
    suspend fun getEpisodeTime(@Path("episodeId") episodeId: String): Response<Time>

    @POST("episodes/{episodeId}/time")
    suspend fun saveEpisodeTime(@Path("episodeId") episodeId: String, @Body time: Time): Response<ResponseBody>

}