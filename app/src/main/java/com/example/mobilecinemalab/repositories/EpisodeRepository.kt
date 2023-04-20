package com.example.mobilecinemalab.repositories

import com.example.mobilecinemalab.datasource.network.Network
import com.example.mobilecinemalab.datasource.network.api.EpisodesApi
import com.example.mobilecinemalab.datasource.network.models.Time
import com.example.mobilecinemalab.domain.repositoryinterfaces.IEpisodeRepository

class EpisodeRepository : IEpisodeRepository, BaseRepository() {
    private val episodesApi: EpisodesApi = Network.getEpisodesApi()

    override fun getCommentsOnEpisode(episodeId: String) =
        apiRequestFlow { episodesApi.getCommentsOnEpisode(episodeId) }

    override fun addCommentsOnEpisode(episodeId: String, commentText: String) =
        apiRequestFlow { episodesApi.addCommentsOnEpisode(episodeId, commentText) }

    override fun getEpisodeTime(episodeId: String) =
        apiRequestFlow { episodesApi.getEpisodeTime(episodeId) }

    override fun saveEpisodeTime(episodeId: String, episodeTime: Int) =
        apiRequestFlow { episodesApi.saveEpisodeTime(episodeId, Time(timeInSeconds = episodeTime) ) }
}