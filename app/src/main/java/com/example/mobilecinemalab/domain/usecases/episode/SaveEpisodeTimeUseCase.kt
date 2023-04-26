package com.example.mobilecinemalab.domain.usecases.episode

import com.example.mobilecinemalab.domain.repositoryinterfaces.IEpisodeRepository
import com.example.mobilecinemalab.repositories.EpisodeRepository

class SaveEpisodeTimeUseCase(private val episodeId: String, private val time: Int) {
    private val episodeRepository: IEpisodeRepository = EpisodeRepository()

    operator fun invoke() = episodeRepository.saveEpisodeTime(episodeId, time)
}