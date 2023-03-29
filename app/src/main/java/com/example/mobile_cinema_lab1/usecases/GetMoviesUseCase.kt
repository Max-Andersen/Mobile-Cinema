package com.example.mobile_cinema_lab1.usecases

import androidx.lifecycle.MutableLiveData
import com.example.mobile_cinema_lab1.TroubleShooting
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Movie
import com.example.mobile_cinema_lab1.network.repositories.MovieRepository
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IMovieRepository
import kotlinx.coroutines.Job

class GetMoviesUseCase(
    private val filter: String,
    private val liveDataForResult: MutableLiveData<ApiResponse<List<Movie>>>
) : BaseUseCase() {
    private val movieRepository: IMovieRepository = MovieRepository()

    suspend operator fun invoke(): Job {
        return baseRequest(
            liveDataForResult,
            TroubleShooting.coroutinesErrorHandler,
            movieRepository.getMoviesByFilter(filter)
        )
    }
}