package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.repositories.MovieRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IMovieRepository

class GetEpisodesOfMovieUseCase(private val movieId: String) {
    private val movieRepository: IMovieRepository = MovieRepository()
    operator fun invoke() = movieRepository.getEpisodeListOfMovie(movieId)
}