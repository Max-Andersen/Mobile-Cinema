package com.example.mobilecinemalab.ui.collections.all

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.domain.usecases.collection.db.DeleteCollectionFromDatabaseUseCase
import com.example.mobilecinemalab.domain.usecases.collection.DeleteCollectionUseCase
import com.example.mobilecinemalab.domain.usecases.collection.db.GetCollectionFromDatabaseUseCase
import com.example.mobilecinemalab.domain.usecases.collection.GetCollectionsUseCase
import com.example.mobilecinemalab.ui.BaseViewModel
import com.example.mobilecinemalab.ui.collections.CollectionUIModel
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
            GetCollectionsUseCase()().collect { data ->
                if (data is ApiResponse.Success){
                    val list = mutableListOf<CollectionUIModel>()
                    runBlocking {
                        data.data.forEach { remoteCollection ->
                            var iconId = "1"
                            var collectionName = remoteCollection.name
                            val collectionId = remoteCollection.collectionId

                            GetCollectionFromDatabaseUseCase(
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

                    val index = userCollections.indexOfFirst { it.name == "Избранное" }

                    Collections.swap(userCollections, if (index == -1) 0 else index, 0)


                    withContext(Dispatchers.Main) {
                        _collections.value = ApiResponse.Success(list)
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