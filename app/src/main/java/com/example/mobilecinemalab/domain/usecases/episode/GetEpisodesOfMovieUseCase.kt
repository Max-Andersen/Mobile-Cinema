package com.example.mobilecinemalab.domain.usecases.episode

import com.example.mobilecinemalab.domain.repositoryinterfaces.IMovieRepository
import com.example.mobilecinemalab.repositories.MovieRepository

class GetEpisodesOfMovieUseCase(private val movieId: String) {
    private val movieRepository: IMovieRepository = MovieRepository()
    operator fun invoke() = movieRepository.getEpisodeListOfMovie(movieId)
}