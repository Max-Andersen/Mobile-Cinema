package com.example.mobile_cinema_lab1.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.datasource.network.models.UserInfo
import com.example.mobile_cinema_lab1.domain.usecases.UserDataUseCase
import com.example.mobile_cinema_lab1.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
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