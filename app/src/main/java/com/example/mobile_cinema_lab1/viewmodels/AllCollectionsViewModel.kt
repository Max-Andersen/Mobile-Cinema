package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Collection
import com.example.mobile_cinema_lab1.usecases.GetCollectionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllCollectionsViewModel : BaseViewModel() {
    private val _collections = MutableLiveData<ApiResponse<List<Collection>>>()

    val userCollections = arrayListOf<Collection>()

    fun getCollectionsLiveData() = _collections

    fun getCollections() {
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            GetCollectionsUseCase()().collect { data ->
                withContext(Dispatchers.Main) {
                    _collections.value = data
                }
            }
        })
    }


    fun saveCollections(collections: List<Collection>){
        userCollections.clear()
        collections.forEach {
            userCollections.add(it)
        }
    }

}