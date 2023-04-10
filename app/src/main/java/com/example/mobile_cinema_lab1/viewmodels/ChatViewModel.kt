package com.example.mobile_cinema_lab1.viewmodels

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.MyApplication
import com.example.mobile_cinema_lab1.additionalmodels.*
import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.network.models.ChatMessage
import com.example.mobile_cinema_lab1.usecases.GetSocketConnectionUseCase
import com.example.mobile_cinema_lab1.usecases.MyWebSocketListener
import com.example.mobile_cinema_lab1.usecases.SendMessageToSocketUseCase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import okhttp3.WebSocket

class ChatViewModel: BaseViewModel() {

    private var validationLiveData = MutableLiveData<String>()

    private var updateStateRecyclerViewLiveData = MutableLiveData<Int>()

    private var waitingForMyMessage = false

    var messages = arrayListOf<ChatUIModel>()

    private var mySentMessageIdx = -1

    private lateinit var socket: WebSocket
    private lateinit var socketListener: MyWebSocketListener

    fun getValidationLiveData() = validationLiveData
    fun getUpdateStateRecyclerViewLiveData() = updateStateRecyclerViewLiveData

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
                        onReceiveData(data)
                    }

                }
                catch (e: Exception){
                    Log.d("!", "FAIL TO PARSE!  ${e.message}")
                }
            }
        })
    }

    private fun onReceiveData(it: ChatMessage){

        val uiModel: ChatUIModel

        if (messages.size != 0) {
            if (getCreationDateByMessageModel(messages.last()) != null) {
                if (getCreationDateByMessageModel(messages.last())!!.toString()
                        .subSequence(0, 10)
                        .toString() < LocalDateTime.parse(it.creationDateTime).toString()
                        .subSequence(0, 10).toString()
                ) {
                    val dateModel =
                        ChatUIModel.DaySeparationModel(DaySeparation(LocalDateTime.parse(it.creationDateTime)))
                    messages.add(dateModel)
                    updateStateRecyclerViewLiveData.value = messages.size - 1
                }
            }

            val prevMessageUserId = getUserIdByMessageModel(messages.last())
            val curMessageUserId = it.authorId

            if (prevMessageUserId == curMessageUserId) {
                if ((messages.last() as? ChatUIModel.MyMessageModel) != null) {
                    (messages.last() as ChatUIModel.MyMessageModel).myMessage.showAvatar =
                        false
                    updateStateRecyclerViewLiveData.value = messages.size - 1
                }
                if ((messages.last() as? ChatUIModel.NotMyMessageModel) != null) {
                    (messages.last() as ChatUIModel.NotMyMessageModel).notMyMessage.showAvatar =
                        false
                    updateStateRecyclerViewLiveData.value = messages.size - 1
                }
            }
        } else {
            val dateModel =
                ChatUIModel.DaySeparationModel(DaySeparation(LocalDateTime.parse(it.creationDateTime)))
            messages.add(dateModel)
            updateStateRecyclerViewLiveData.value = messages.size - 1
        }

        if (it.authorId == Network.getSharedPrefs(MyApplication.UserId)) {

            if (waitingForMyMessage) {
                waitingForMyMessage = false
                (messages.last() as ChatUIModel.MyMessageModel).myMessage.apply {
                    creationDate = LocalDateTime.parse(it.creationDateTime)
                    authorName = it.authorName
                    authorAvatar = it.authorAvatar
                    showAvatar = true
                    isLoading = false
                }
            } else {
                uiModel = ChatUIModel.MyMessageModel(
                    MyMessage(
                        creationDate = LocalDateTime.parse(it.creationDateTime),
                        authorId = it.authorId,
                        authorAvatar = it.authorAvatar,
                        authorName = it.authorName,
                        text = it.text,
                        isLoading = false
                    )
                )
                messages.add(uiModel)
            }
        } else {
            uiModel = ChatUIModel.NotMyMessageModel(
                NotMyMessage(
                    creationDate = LocalDateTime.parse(it.creationDateTime),
                    authorId = it.authorId,
                    authorAvatar = it.authorAvatar,
                    authorName = it.authorName,
                    text = it.text,
                )
            )
            messages.add(uiModel)
        }
        updateStateRecyclerViewLiveData.value = messages.size - 1
    }

    fun closeSocket(){
        waitingForMyMessage = false
        socket.close(1000, "Ok")
    }

    fun sendMessage(messageText: String){
        if (messageText.isNotBlank()){
            messages.add(
                ChatUIModel.MyMessageModel(
                    MyMessage(
                        Clock.System.now().toLocalDateTime(
                            TimeZone.UTC
                        ),
                        authorId = Network.getSharedPrefs(MyApplication.UserId) ?: "",
                        authorName = "",
                        authorAvatar = null,
                        text = messageText,
                        showAvatar = true,
                        isLoading = true,
                    )
                )
            )

            mySentMessageIdx = messages.size - 1

            updateStateRecyclerViewLiveData.value = messages.size - 1

            waitingForMyMessage = true
            SendMessageToSocketUseCase(socket)(messageText)

            viewModelScope.launch(Dispatchers.IO) {
                Thread.sleep(5000)

                withContext(Dispatchers.Main){
                    if (waitingForMyMessage){
                        (messages[mySentMessageIdx] as ChatUIModel.MyMessageModel).myMessage.fail = true
                        updateStateRecyclerViewLiveData.value = mySentMessageIdx
                    }
                }
            }
        } else{
            validationLiveData.value = "Message is empty!"
        }
    }
}