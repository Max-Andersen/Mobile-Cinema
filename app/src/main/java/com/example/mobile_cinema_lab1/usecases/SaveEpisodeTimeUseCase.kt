package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.repositories.EpisodeRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IEpisodeRepository

class SaveEpisodeTimeUseCase(private val episodeId: String, private val time: Int) {
    private val episodeRepository: IEpisodeRepository = EpisodeRepository()

    operator fun invoke() = episodeRepository.saveEpisodeTime(episodeId, time)
}