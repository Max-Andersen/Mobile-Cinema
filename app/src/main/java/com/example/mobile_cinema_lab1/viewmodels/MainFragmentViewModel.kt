package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.CoverImage
import com.example.mobile_cinema_lab1.network.models.Movie
import com.example.mobile_cinema_lab1.usecases.GetCoverImageUseCase
import com.example.mobile_cinema_lab1.usecases.GetMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragmentViewModel: BaseViewModel() {
    private val mJobs = mutableListOf<Job>()

    private val inTrendMoviesLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    private val youWatchedMovieLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    private val newMoviesLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    private val moviesForYouLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    private val coverImageLiveData = MutableLiveData<ApiResponse<CoverImage>>()

    var inTrendMovies = arrayListOf<Movie>()
    var newMovies = arrayListOf<Movie>()
    var moviesForYou = arrayListOf<Movie>()

    fun getLiveDataForInTrendMovies() = inTrendMoviesLiveData
    fun getLiveDataForYouWatchedMovie() = youWatchedMovieLiveData
    fun getLiveDataForNewMovies() = newMoviesLiveData
    fun getLiveDataForMoviesForYou() = moviesForYouLiveData
    fun getLiveDataForCoverImage() = coverImageLiveData

    fun getMovies(){

        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            GetMoviesUseCase("new")().collect{
                withContext(Dispatchers.Main){
                    newMoviesLiveData.value = it
                }
            }
        })

        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            GetMoviesUseCase("lastView")().collect{
                withContext(Dispatchers.Main){
                    youWatchedMovieLiveData.value = it
                }
            }
        })

        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            GetMoviesUseCase("forMe")().collect{
                withContext(Dispatchers.Main){
                    moviesForYouLiveData.value = it
                }
            }
        })

        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            GetMoviesUseCase("inTrend")().collect{
                withContext(Dispatchers.Main){
                    inTrendMoviesLiveData.value = it
                }
            }
        })

        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            GetCoverImageUseCase()().collect{
                withContext(Dispatchers.Main){
                    coverImageLiveData.value = it
                }
            }
        })


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