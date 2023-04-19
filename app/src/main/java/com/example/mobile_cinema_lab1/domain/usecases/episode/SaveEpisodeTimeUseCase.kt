package com.example.mobile_cinema_lab1.domain.usecases.episode

import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.IEpisodeRepository
import com.example.mobile_cinema_lab1.repositories.EpisodeRepository

class SaveEpisodeTimeUseCase(private val episodeId: String, private val time: Int) {
    private val episodeRepository: IEpisodeRepository = EpisodeRepository()

    operator fun invoke() = episodeRepository.saveEpisodeTime(episodeId, time)
}