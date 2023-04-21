package com.example.mobilecinemalab.domain.usecases.movie

import com.example.mobilecinemalab.domain.repositoryinterfaces.IMovieRepository
import com.example.mobilecinemalab.repositories.MovieRepository

class GetHistoryUseCase {
    private val movieRepository: IMovieRepository = MovieRepository()

    operator fun invoke() = movieRepository.getUserHistory()
}