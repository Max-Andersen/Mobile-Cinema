package com.example.mobile_cinema_lab1.usecases

import com.example.mobile_cinema_lab1.network.repositories.MovieRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IMovieRepository

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