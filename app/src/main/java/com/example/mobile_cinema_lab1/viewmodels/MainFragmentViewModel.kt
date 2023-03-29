package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Movie
import com.example.mobile_cinema_lab1.usecases.GetMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainFragmentViewModel: BaseViewModel() {
    private val mJobs = mutableListOf<Job>()

    private val inTrendMoviesLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    private val youWatchedMovieLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    private val newMoviesLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    private val moviesForYouLiveData = MutableLiveData<ApiResponse<List<Movie>>>()

    var inTrendMovies = arrayListOf<Movie>()
    var newMovies = arrayListOf<Movie>()
    var moviesForYou = arrayListOf<Movie>()

    fun getLiveDataForInTrendMovies() = inTrendMoviesLiveData
    fun getLiveDataForYouWatchedMovie() = youWatchedMovieLiveData
    fun getLiveDataForNewMovies() = newMoviesLiveData
    fun getLiveDataForMoviesForYou() = moviesForYouLiveData

    fun getMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            mJobs.add(GetMoviesUseCase("new", newMoviesLiveData)())
            mJobs.add(GetMoviesUseCase("inTrend", inTrendMoviesLiveData)())
            mJobs.add(GetMoviesUseCase("forMe", moviesForYouLiveData)())
            mJobs.add(GetMoviesUseCase("lastView", youWatchedMovieLiveData)())
        }
    }

    override fun onCleared() {
        super.onCleared()
        mJobs.forEach{
            if (it.isActive) {
                it.cancel()
            }
        }
    }
}