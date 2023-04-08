package com.example.mobile_cinema_lab1.viewmodels

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.network.models.ChatMessage
import com.example.mobile_cinema_lab1.usecases.GetSocketConnectionUseCase
import com.example.mobile_cinema_lab1.usecases.MyWebSocketListener
import com.example.mobile_cinema_lab1.usecases.SendMessageToSocketUseCase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.WebSocket


class ChatViewModel: BaseViewModel() {

    private var chatLiveData = MutableLiveData<ChatMessage>()

    private lateinit var socket: WebSocket
    private lateinit var socketListener: MyWebSocketListener

    fun getChatLiveData() = chatLiveData

    fun getSocket(chatId: String){
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            val url = Network.BASE_URL.replace("http", "ws") + "chats/$chatId/messages"
            val networkData = GetSocketConnectionUseCase(url)()
            socket = networkData.first
            socketListener = networkData.second
            socketListener.getFlow().collect{
                try {
                    val gson = Gson()
                    val data = gson.fromJson(it, ChatMessage::class.java)
                    Log.d("!", data.toString())
                    Handler(Looper.getMainLooper()).post{
                        chatLiveData.value = data
                    }

                }
                catch (e: Exception){
                    Log.d("!", "FAIL TO PARSE!  ${e.message}")
                }
            }
        })
    }

    fun closeSocket(){
        socket.close(1000, "Ok")
    }

    fun sendMessage(messageText: String){
        // TODO(ВАЛИДАЦИЯ)
        if (messageText.isNotBlank()){
            SendMessageToSocketUseCase(socket)(messageText)
        }

    }

}