package com.example.mobile_cinema_lab1.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.datasource.network.models.ChatMessage
import com.example.mobile_cinema_lab1.domain.usecases.*
import com.example.mobile_cinema_lab1.domain.usecases.socket.CloseSocketUseCase
import com.example.mobile_cinema_lab1.domain.usecases.socket.GetMessagesFromSocketUseCase
import com.example.mobile_cinema_lab1.domain.usecases.socket.GetSocketConnectionUseCase
import com.example.mobile_cinema_lab1.domain.usecases.socket.SendMessageToSocketUseCase
import com.example.mobile_cinema_lab1.ui.BaseViewModel
import com.example.mobile_cinema_lab1.ui.chat.additionalmodels.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ChatViewModel : BaseViewModel() {

    private var validationLiveData = MutableLiveData<String>()

    private var updateStateRecyclerViewLiveData = MutableLiveData<Int>()

    private var waitingForMyMessage = false

    var messages = arrayListOf<ChatUIModel>()

    private var mySentMessageIdx = -1

    private val sharedPreferencesUseCase =
        SharedPreferencesUseCase()

    fun getValidationLiveData() = validationLiveData
    fun getUpdateStateRecyclerViewLiveData() = updateStateRecyclerViewLiveData

    fun getSocket(chatId: String) {
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            GetSocketConnectionUseCase(chatId)()

            GetMessagesFromSocketUseCase()().collect {
                withContext(Dispatchers.Main){
                    onReceiveData(it)
                }
            }
        })
    }

    private fun onReceiveData(it: ChatMessage) {
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
                when (messages.last()) {
                    is ChatUIModel.MyMessageModel -> {
                        (messages.last() as ChatUIModel.MyMessageModel).myMessage.showAvatar =
                            false
                        updateStateRecyclerViewLiveData.value = messages.size - 1
                    }
                    is ChatUIModel.NotMyMessageModel -> {
                        (messages.last() as ChatUIModel.NotMyMessageModel).notMyMessage.showAvatar =
                            false
                        updateStateRecyclerViewLiveData.value = messages.size - 1
                    }
                    else -> {}
                }
            }
        } else {
            val dateModel =
                ChatUIModel.DaySeparationModel(DaySeparation(LocalDateTime.parse(it.creationDateTime)))
            messages.add(dateModel)
            updateStateRecyclerViewLiveData.value = messages.size - 1
        }

        if (it.authorId == sharedPreferencesUseCase.getUserId()) {

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

    fun closeSocket() {
        waitingForMyMessage = false
        CloseSocketUseCase()()
    }

    fun sendMessage(messageText: String) {
        if (messageText.isNotBlank()) {
            messages.add(
                ChatUIModel.MyMessageModel(
                    MyMessage(
                        Clock.System.now().toLocalDateTime(
                            TimeZone.UTC
                        ),
                        authorId = sharedPreferencesUseCase.getUserId() ?: "",
                        authorName = sharedPreferencesUseCase.getUserName() ?: "",
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
            SendMessageToSocketUseCase(
                messageText
            )()

            viewModelScope.launch(Dispatchers.IO) {
                Thread.sleep(5000)

                withContext(Dispatchers.Main) {
                    if (waitingForMyMessage) {
                        (messages[mySentMessageIdx] as ChatUIModel.MyMessageModel).myMessage.fail =
                            true
                        updateStateRecyclerViewLiveData.value = mySentMessageIdx
                    }
                }
            }
        } else {
            validationLiveData.value = "Message is empty!"
        }
    }
}