package com.example.mobilecinemalab.ui.collections.edit

import androidx.lifecycle.viewModelScope
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.domain.usecases.collection.db.DeleteCollectionFromDatabaseUseCase
import com.example.mobilecinemalab.domain.usecases.collection.DeleteCollectionUseCase
import com.example.mobilecinemalab.domain.usecases.collection.db.GetCollectionFromDatabaseUseCase
import com.example.mobilecinemalab.domain.usecases.collection.db.InsertCollectionToDatabaseUseCase
import com.example.mobilecinemalab.domain.usecases.collection.db.UpdateCollectionInDatabaseUseCase
import com.example.mobilecinemalab.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditCollectionViewModel: BaseViewModel() {
    var selectedIcon = ""

    fun saveChanges(collectionId: String, collectionName: String){
        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            if (GetCollectionFromDatabaseUseCase(collectionId)() != null){
                UpdateCollectionInDatabaseUseCase(collectionId, collectionName, selectedIcon)()
            } else{
                InsertCollectionToDatabaseUseCase(collectionId, collectionName, selectedIcon)()
            }
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