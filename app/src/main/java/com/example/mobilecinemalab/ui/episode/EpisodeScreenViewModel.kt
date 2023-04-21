package com.example.mobilecinemalab.ui.episode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.Time
import com.example.mobilecinemalab.domain.usecases.collection.AddMovieToCollectionUseCase
import com.example.mobilecinemalab.domain.usecases.collection.DeleteMovieFromCollectionUseCase
import com.example.mobilecinemalab.domain.usecases.collection.GetCollectionsUseCase
import com.example.mobilecinemalab.domain.usecases.collection.GetMoviesInCollectionUseCase
import com.example.mobilecinemalab.domain.usecases.collection.db.GetCollectionFromDatabaseUseCase
import com.example.mobilecinemalab.domain.usecases.episode.GetEpisodeTimeUseCase
import com.example.mobilecinemalab.domain.usecases.episode.SaveEpisodeTimeUseCase
import com.example.mobilecinemalab.ui.BaseViewModel
import com.example.mobilecinemalab.ui.collections.CollectionUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class EpisodeScreenViewModel : BaseViewModel() {

    private val episodeLiveData = MutableLiveData<ApiResponse<Time>>()

    private val addToCollectionLiveData = MutableLiveData<ApiResponse<ResponseBody>>()

    private val navigationUpAccept = MutableLiveData(false)

    private val likeState = MutableLiveData<Boolean>()

    var collectionsLoaded = false

    var isFavouriteLoaded = false

    var collectionList = mutableListOf<CollectionUIModel>()

    private lateinit var favouriteCollection: String

    private lateinit var movieId: String

    fun getLiveDataForEpisodeTime() = episodeLiveData

    fun getLiveDataForAddToCollection() = addToCollectionLiveData

    fun getLiveDataForNavigationUp() = navigationUpAccept

    fun getLiveDataForLikeState() = likeState

    fun navigationSuccessful() {
        navigationUpAccept.value = false
    }

    fun getEpisodeTime(episodeId: String) {
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            GetEpisodeTimeUseCase(
                episodeId
            )().collect { data ->
                withContext(Dispatchers.Main) {
                    episodeLiveData.value = data
                }
            }
        })
    }

    fun saveEpisodeTime(episodeId: String, currentTime: Int, isNavigateUp: Boolean = false) {
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            SaveEpisodeTimeUseCase(
                episodeId,
                currentTime
            )().collect {
                withContext(Dispatchers.Main) {
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

    fun getCollections(movieId: String) {
        this@EpisodeScreenViewModel.movieId = movieId
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            GetCollectionsUseCase()().collect {
                when (it) {
                    is ApiResponse.Success -> {
                        val list = mutableListOf<CollectionUIModel>()
                        it.data.forEach { collection ->
                            var collectionName = collection.name
                            GetCollectionFromDatabaseUseCase(
                                collection.collectionId
                            )()?.let { dbModel ->
                                collectionName = dbModel.name
                            }

                            if (collectionName == "Избранное") {
                                favouriteCollection = collection.collectionId
                                GetMoviesInCollectionUseCase(collection.collectionId)().collect { favourite ->
                                    if (favourite is ApiResponse.Success) {
                                        withContext(Dispatchers.Main) {
                                            isFavouriteLoaded = true
                                            likeState.value = favourite.data.find { movie ->
                                                movie.movieId == movieId
                                            } != null
                                        }
                                    }
                                }
                            }

                            list.add(
                                CollectionUIModel(
                                    collection.collectionId,
                                    collectionName,
                                    "1"
                                )
                            )
                        }

                        collectionList.clear()

                        list.forEach { brr ->
                            collectionList.add(brr)
                        }

                        collectionsLoaded = true
                    }
                    is ApiResponse.Failure -> {}
                    is ApiResponse.Loading -> {}
                }
            }
        })
    }

    fun addMovieToCollection(position: Int, movieId: String) {
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            AddMovieToCollectionUseCase(
                collectionList[position].collectionId,
                movieId
            )().collect {
                withContext(Dispatchers.Main) {
                    addToCollectionLiveData.value = it
                }
            }
        })
    }

    fun changeState() {
        if (likeState.value == true) {
            mJobs.add(viewModelScope.launch(Dispatchers.IO) {
                DeleteMovieFromCollectionUseCase(favouriteCollection, movieId)().collect()
            })
        } else {
            mJobs.add(viewModelScope.launch(Dispatchers.IO) {
                AddMovieToCollectionUseCase(favouriteCollection, movieId)().collect()
            })
        }
        likeState.value = !likeState.value!!
    }


}