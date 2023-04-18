package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.usecases.DeleteCollectionFromDatabaseUseCase
import com.example.mobile_cinema_lab1.usecases.DeleteCollectionUseCase
import com.example.mobile_cinema_lab1.usecases.UpdateCollectionInDatabaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditCollectionViewModel: BaseViewModel() {
    var selectedIcon = ""


    fun saveChanges(collectionId: String, collectionName: String){
        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            UpdateCollectionInDatabaseUseCase(collectionId, collectionName, selectedIcon)()
        })
    }

    fun deleteCollection(collectionId: String){
        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            DeleteCollectionUseCase(collectionId)().collect{
                if (it is ApiResponse.Success){
                    DeleteCollectionFromDatabaseUseCase(collectionId)()
                }
            }
        })
    }
}