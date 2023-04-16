package com.example.mobile_cinema_lab1.network.repositories

import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.network.api.EpisodesApi
import com.example.mobile_cinema_lab1.network.models.Time
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IEpisodeRepository

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