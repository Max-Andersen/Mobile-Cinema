package com.example.mobilecinemalab.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.ChatWithLastMessage
import com.example.mobilecinemalab.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActiveUserChatsViewModel: BaseViewModel() {

    private val gotChats = MutableLiveData<ApiResponse<List<ChatWithLastMessage>>>()

    val chatList = mutableListOf<ChatWithLastMessage>()

    fun getChatsLiveData() = gotChats

    fun getChats(){
        gotChats.value = ApiResponse.Loading
        chatList.clear()

        mJobs.add(viewModelScope.launch(Dispatchers.IO){
            com.example.mobilecinemalab.domain.usecases.GetUserChatsUseCase()().collect{ data ->
                if (data is ApiResponse.Success){
                    data.data.forEach {
                        chatList.add(it)
                    }
                }
                withContext(Dispatchers.Main){
                    gotChats.value = data
                }
            }
        })
    }


}