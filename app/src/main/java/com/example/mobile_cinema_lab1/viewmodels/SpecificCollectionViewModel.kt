package com.example.mobile_cinema_lab1.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Movie
import com.example.mobile_cinema_lab1.usecases.DeleteMovieFromCollectionUseCase
import com.example.mobile_cinema_lab1.usecases.GetMoviesInCollectionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpecificCollectionViewModel : BaseViewModel() {
    private val collectionItemsLiveData = MutableLiveData<ApiResponse<List<Movie>>>()

    private lateinit var collectionId: String

    fun getCollectionItemsLiveData() = collectionItemsLiveData

    val movies = arrayListOf<Movie>()


    fun getItems(collectionId: String) {
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            GetMoviesInCollectionUseCase(collectionId)().collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is ApiResponse.Loading -> {}
                        is ApiResponse.Failure -> {
                            collectionItemsLiveData.value = it
                        }
                        is ApiResponse.Success -> {
                            this@SpecificCollectionViewModel.collectionId = collectionId
                            movies.clear()
                            it.data.forEach { movie ->
                                movies.add(movie)
                            }
                            collectionItemsLiveData.value = it
                        }
                    }
                }
            }
        })
    }

    fun deleteMovieFromCollection(movie: Movie){
        Log.d("!", "GOING TO DELETE")
        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            DeleteMovieFromCollectionUseCase(collectionId, movie.movieId)().collect{

            }
        })
        movies.remove(movie)
    }


}