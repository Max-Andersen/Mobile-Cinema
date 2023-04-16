package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.repositories.EpisodeRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IEpisodeRepository

class GetEpisodeTimeUseCase(private val episodeId: String) {
    private val episodeRepository: IEpisodeRepository = EpisodeRepository()

    operator fun invoke() = episodeRepository.getEpisodeTime(episodeId)

}