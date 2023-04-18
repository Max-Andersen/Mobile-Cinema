package com.example.mobile_cinema_lab1.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.CollectionUIModel
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Collection
import com.example.mobile_cinema_lab1.network.models.MovieId
import com.example.mobile_cinema_lab1.network.models.Time
import com.example.mobile_cinema_lab1.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class EpisodeScreenViewModel: BaseViewModel() {

    private val episodeLiveData = MutableLiveData<ApiResponse<Time>>()

    private val addToCollectionLiveData = MutableLiveData<ApiResponse<ResponseBody>>()

    private val navigationUpAccept = MutableLiveData(false)

    private val navigationToChatAccept = MutableLiveData(false)

    var collectionsLoaded = false

    var collectionList = mutableListOf<CollectionUIModel>()

    fun getLiveDataForEpisodeTime() = episodeLiveData
    fun getLiveDataForAddToCollection() = addToCollectionLiveData

    fun getLiveDataForNavigationUp() = navigationUpAccept

    fun navigationSuccessful(){
        navigationUpAccept.value = false
    }

    fun getEpisodeTime(episodeId: String){
        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            GetEpisodeTimeUseCase(episodeId)().collect{data ->
                withContext(Dispatchers.Main){
                    episodeLiveData.value = data
                }
            }
        })
    }

    fun saveEpisodeTime(episodeId: String, currentTime: Int, isNavigateUp: Boolean = false){
        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            SaveEpisodeTimeUseCase(episodeId, currentTime)().collect{
                withContext(Dispatchers.Main){
                    when (it) {
                        is ApiResponse.Loading -> {
                            Log.d("!", "LOAD")
                        }
                        is ApiResponse.Failure -> {
                            if (isNavigateUp) navigationUpAccept.value = true
                        }
                        is ApiResponse.Success -> {
                            if (isNavigateUp) navigationUpAccept.value = true
                        }
                    }
                }

            }
        })
    }

    fun getCollections(){
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            GetCollectionsUseCase()().collect{
                when (it){
                    is ApiResponse.Success -> {
                        val list = mutableListOf<CollectionUIModel>()
                        it.data.forEach { collection ->
                            var collectionName = collection.name
                            GetCollectionFromDatabaseUseCase(collection.collectionId)()?.let { dbModel ->
                                collectionName = dbModel.name
                            }
                            list.add(CollectionUIModel(collection.collectionId, collectionName, "1"))
                        }

                        collectionList.clear()

                        list.forEach{ brr ->
                            collectionList.add(brr)
                        }

                        collectionsLoaded = true
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
                withContext(Dispatchers.Main){
                    addToCollectionLiveData.value = it
                }
            }
        })
    }
}