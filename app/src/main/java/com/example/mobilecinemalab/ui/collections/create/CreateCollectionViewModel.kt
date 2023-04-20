package com.example.mobilecinemalab.ui.collections.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.mobilecinemalab.datasource.network.models.Collection
import com.example.mobilecinemalab.domain.usecases.collection.CreateCollectionUseCase
import com.example.mobilecinemalab.domain.usecases.collection.db.InsertCollectionToDatabaseUseCase

class CreateCollectionViewModel: BaseViewModel() {

    private val createCollectionLiveData = MutableLiveData<ApiResponse<Collection>>()

    var selectedIcon = "1"

    fun getCreateCollectionLiveData() = createCollectionLiveData


    fun createCollection(collectionName: String){
        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            if (collectionName == "Избранное"){
                withContext(Dispatchers.Main){
                    createCollectionLiveData.value = ApiResponse.Failure("Нельзя назвать коллекцию \"Избранное\"!", "400")
                }
            } else{
                CreateCollectionUseCase(
                    collectionName
                )().collect{ data ->
                    withContext(Dispatchers.Main){
                        createCollectionLiveData.value = data
                    }
                    if (data is ApiResponse.Success){
                        InsertCollectionToDatabaseUseCase(
                            data.data.collectionId,
                            collectionName,
                            selectedIcon
                        )()
                    }
                }
            }
        })
    }




}