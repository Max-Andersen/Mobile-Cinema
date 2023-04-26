package com.example.mobilecinemalab.domain.usecases

import com.example.mobilecinemalab.domain.repositoryinterfaces.IMovieRepository
import com.example.mobilecinemalab.repositories.MovieRepository

class SetDislikeOnMovie(private val movieId: String) {
    private val movieRepository: IMovieRepository = MovieRepository()

    operator fun invoke() = movieRepository.removeMovieFromCompilation(movieId)

}