package com.example.mobilecinemalab.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobilecinemalab.datasource.network.models.CoverImage
import com.example.mobilecinemalab.datasource.network.models.Movie
import com.example.mobilecinemalab.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.mobilecinemalab.datasource.network.*
import com.example.mobilecinemalab.domain.usecases.movie.GetMoviesUseCase

class MainFragmentViewModel: BaseViewModel() {

    private val inTrendMoviesLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    private val youWatchedMovieLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    private val newMoviesLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    private val moviesForYouLiveData = MutableLiveData<ApiResponse<List<Movie>>>()
    private val coverImageLiveData = MutableLiveData<ApiResponse<CoverImage>>()

    private val itemsLoadedLiveData = MutableLiveData(0)


    var inTrendMovies = arrayListOf<Movie>()
    var newMovies = arrayListOf<Movie>()
    var moviesForYou = arrayListOf<Movie>()

    fun saveInTrendMovies(movies: List<Movie>){
        inTrendMovies.clear()
        movies.forEach{
            inTrendMovies.add(it)
        }
    }

    fun saveNewMovies(movies: List<Movie>){
        newMovies.clear()
        movies.forEach{
            newMovies.add(it)
        }
    }

    fun saveForYouMovies(movies: List<Movie>){
        moviesForYou.clear()
        movies.forEach{
            moviesForYou.add(it)
        }
    }

    fun itemLoaded(){
        itemsLoadedLiveData.value = itemsLoadedLiveData.value?.plus(1)
    }


    fun getLiveDataForInTrendMovies() = inTrendMoviesLiveData
    fun getLiveDataForYouWatchedMovie() = youWatchedMovieLiveData
    fun getLiveDataForNewMovies() = newMoviesLiveData
    fun getLiveDataForMoviesForYou() = moviesForYouLiveData
    fun getLiveDataForCoverImage() = coverImageLiveData

    fun getLiveDataForLoadedItems() = itemsLoadedLiveData

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
            com.example.mobilecinemalab.domain.usecases.GetCoverImageUseCase()().collect{
                withContext(Dispatchers.Main){
                    coverImageLiveData.value = it
                }
            }
        })
    }
}