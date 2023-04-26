package com.example.mobilecinemalab.domain.usecases.movie

import com.example.mobilecinemalab.domain.repositoryinterfaces.IMovieRepository
import com.example.mobilecinemalab.repositories.MovieRepository

class GetMoviesUseCase(
    private val filter: String,
    //private val liveDataForResult: MutableLiveData<ApiResponse<List<Movie>>>
) { //: BaseUseCase()
    private val movieRepository: IMovieRepository = MovieRepository()

    operator fun invoke() = movieRepository.getMoviesByFilter(filter)

//    suspend operator fun invoke(): Job {
//        return baseRequest(
//            liveDataForResult,
//            TroubleShooting.coroutinesErrorHandler,
//            movieRepository.getMoviesByFilter(filter)
//        )
//    }
}