package com.example.mobile_cinema_lab1.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.usecases.GetCollectionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.mobile_cinema_lab1.network.models.Collection
import com.example.mobile_cinema_lab1.network.models.MovieId
import com.example.mobile_cinema_lab1.usecases.AddMovieToCollectionUseCase
import kotlinx.coroutines.flow.collect

class EpisodeScreenViewModel: BaseViewModel() {

    var collectionsLoaded = false

    lateinit var collectionList: List<Collection>

    fun getCollections(){
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            GetCollectionsUseCase()().collect{
                when (it){
                    is ApiResponse.Success -> {
                        collectionsLoaded = true
                        collectionList = it.data
                    }
                    is ApiResponse.Failure -> { }
                    is ApiResponse.Loading -> { }
                }
            }
        })
    }

    fun addMovieToCollection(position: Int, movieId: String){
        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            AddMovieToCollectionUseCase(collectionList[position].collectionId, MovieId(movieId = movieId) )().collect{
                when(it){
                    is ApiResponse.Loading -> {  }
                    is ApiResponse.Failure -> { Log.d("!", "FAIL ADD TO COLLECTION") }
                    is ApiResponse.Success -> { Log.d("!", "SUCCESS ADD TO COLLECTION") }
                }
            }
        })
    }
}