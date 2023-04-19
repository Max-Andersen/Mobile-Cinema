package com.example.mobile_cinema_lab1.ui.collections.edit

import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.domain.usecases.DeleteCollectionFromDatabaseUseCase
import com.example.mobile_cinema_lab1.domain.usecases.DeleteCollectionUseCase
import com.example.mobile_cinema_lab1.domain.usecases.UpdateCollectionInDatabaseUseCase
import com.example.mobile_cinema_lab1.ui.BaseViewModel
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