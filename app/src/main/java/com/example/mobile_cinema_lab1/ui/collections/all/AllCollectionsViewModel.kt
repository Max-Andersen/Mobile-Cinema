package com.example.mobile_cinema_lab1.ui.collections.all

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.domain.usecases.DeleteCollectionFromDatabaseUseCase
import com.example.mobile_cinema_lab1.domain.usecases.DeleteCollectionUseCase
import com.example.mobile_cinema_lab1.ui.BaseViewModel
import com.example.mobile_cinema_lab1.ui.collections.CollectionUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class AllCollectionsViewModel : BaseViewModel() {
    private val _collections = MutableLiveData<ApiResponse<List<CollectionUIModel>>>()

    var userCollections = mutableListOf<CollectionUIModel>()

    fun getCollectionsLiveData() = _collections

    fun getCollections() {
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            com.example.mobile_cinema_lab1.domain.usecases.GetCollectionsUseCase()().collect { data ->
                if (data is ApiResponse.Success){
                    val list = mutableListOf<CollectionUIModel>()
                    runBlocking {
                        data.data.forEach { remoteCollection ->
                            var iconId = "1"
                            var collectionName = remoteCollection.name
                            val collectionId = remoteCollection.collectionId

                            com.example.mobile_cinema_lab1.domain.usecases.GetCollectionFromDatabaseUseCase(
                                collectionId
                            )()?.let {
                                iconId = it.iconId
                                collectionName = it.name
                            }
                            list.add(CollectionUIModel(collectionId, collectionName, iconId))
                        }
                    }

                    userCollections.clear()
                    list.forEach{
                        userCollections.add(it)
                    }

                    Collections.swap(userCollections, userCollections.indexOfFirst { it.name == "Избранное" }, 0)


                    withContext(Dispatchers.Main) {
                        _collections.value = ApiResponse.Success(list)
                    }
                }

                if (data is ApiResponse.Failure){
                    withContext(Dispatchers.Main) {
                        _collections.value = ApiResponse.Success(listOf())
                    }
                }

            }
        })
    }

    fun deleteCollection(collectionPosition: Int){
        val collectionId = userCollections[collectionPosition].collectionId
        userCollections.removeAt(collectionPosition)
        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            DeleteCollectionUseCase(
                collectionId
            )().collect{
                if (it is ApiResponse.Success){
                    DeleteCollectionFromDatabaseUseCase(
                        collectionId
                    )()
                }
            }
        })
    }
}