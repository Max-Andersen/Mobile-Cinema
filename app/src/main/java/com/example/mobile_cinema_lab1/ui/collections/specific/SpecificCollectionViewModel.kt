package com.example.mobile_cinema_lab1.ui.collections.specific

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.datasource.network.models.Movie
import com.example.mobile_cinema_lab1.domain.usecases.collection.DeleteMovieFromCollectionUseCase
import com.example.mobile_cinema_lab1.domain.usecases.collection.GetMoviesInCollectionUseCase
import com.example.mobile_cinema_lab1.ui.BaseViewModel
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
            GetMoviesInCollectionUseCase(
                collectionId
            )().collect {
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

    fun deleteMovieFromCollection(moviePosition: Int){
        val movieId = movies[moviePosition].movieId
        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            DeleteMovieFromCollectionUseCase(
                collectionId,
                movieId
            )().collect{

            }
        })
        movies.removeAt(moviePosition)
    }


}