package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Collection
import com.example.mobile_cinema_lab1.usecases.CreateCollectionUseCase
import com.example.mobile_cinema_lab1.usecases.InsertCollectionToDatabaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                CreateCollectionUseCase(collectionName)().collect{data ->
                    withContext(Dispatchers.Main){
                        createCollectionLiveData.value = data
                    }
                    if (data is ApiResponse.Success){
                        InsertCollectionToDatabaseUseCase(data.data.collectionId, collectionName, selectedIcon)()
                    }
                }
            }
        })
    }




}