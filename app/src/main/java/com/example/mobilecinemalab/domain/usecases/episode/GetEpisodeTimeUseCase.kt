package com.example.mobilecinemalab.domain.usecases.episode

import com.example.mobilecinemalab.domain.repositoryinterfaces.IEpisodeRepository
import com.example.mobilecinemalab.repositories.EpisodeRepository

class GetEpisodeTimeUseCase(private val episodeId: String) {
    private val episodeRepository: IEpisodeRepository = EpisodeRepository()

    operator fun invoke() = episodeRepository.getEpisodeTime(episodeId)

}