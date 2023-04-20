package com.example.mobilecinemalab.ui.profile.photo

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobilecinemalab.datasource.network.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.mobilecinemalab.domain.usecases.SendAvatarUseCase
import com.example.mobilecinemalab.ui.BaseViewModel
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class ProfileAvatarDialogViewModel : BaseViewModel() {

    private val sendingAvatarLiveData = MutableLiveData<ApiResponse<ResponseBody>>()

    fun getSendingAvatarLiveData() = sendingAvatarLiveData

    fun sendAvatarImage(image: Bitmap?) {
        if (image != null){
            mJobs.add(viewModelScope.launch(Dispatchers.IO) {
                SendAvatarUseCase(image)().collect{ response ->
                    withContext(Dispatchers.Main){
                        sendingAvatarLiveData.value = response
                    }
                }
            })
        }

    }
}