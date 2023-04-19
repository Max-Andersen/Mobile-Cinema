package com.example.mobile_cinema_lab1.domain.usecases

import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.IMovieRepository
import com.example.mobile_cinema_lab1.repositories.MovieRepository

class SetDislikeOnMovie(private val movieId: String) {
    private val movieRepository: IMovieRepository = MovieRepository()

    operator fun invoke() = movieRepository.removeMovieFromCompilation(movieId)

}