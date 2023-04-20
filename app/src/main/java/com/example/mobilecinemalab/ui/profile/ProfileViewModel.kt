package com.example.mobilecinemalab.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.UserInfo
import com.example.mobilecinemalab.domain.usecases.UserDataUseCase
import com.example.mobilecinemalab.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel: BaseViewModel() {

    private val userLiveData = MutableLiveData<ApiResponse<UserInfo>>()

    fun getLiveDataForUseInfo() = userLiveData

    private val userUseCase = UserDataUseCase()

    var avatar: String? = null

    fun getUserInfo(){
        mJobs.add(
            viewModelScope.launch(Dispatchers.IO) {
                userUseCase.getUserData().collect{ response ->
                    withContext(Dispatchers.Main){
                        if (response is ApiResponse.Success){
                            avatar = response.data.avatar
                        }
                        userLiveData.value = response
                    }
                }
            }
        )
    }

    fun loadUserAvatar(){

    }

}