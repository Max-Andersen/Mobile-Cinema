package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.UserInfo
import com.example.mobile_cinema_lab1.usecases.UserDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel: BaseViewModel() {

    private val userLiveData = MutableLiveData<ApiResponse<UserInfo>>()

    fun getLiveDataForUseInfo() = userLiveData

    private val userUseCase = UserDataUseCase()

    fun getUserInfo(){
        mJobs.add(
            viewModelScope.launch(Dispatchers.IO) {
                userUseCase.getUserData().collect{ response ->
                    withContext(Dispatchers.Main){
                        userLiveData.value = response
                    }
                }
            }
        )
    }

    fun loadUserAvatar(){

    }

}