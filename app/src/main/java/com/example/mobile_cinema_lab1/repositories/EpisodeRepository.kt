package com.example.mobile_cinema_lab1.repositories

import com.example.mobile_cinema_lab1.datasource.network.Network
import com.example.mobile_cinema_lab1.datasource.network.api.EpisodesApi
import com.example.mobile_cinema_lab1.datasource.network.models.Time
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.IEpisodeRepository

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