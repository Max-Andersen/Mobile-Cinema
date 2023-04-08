package com.example.mobile_cinema_lab1.additionalmodels

import androidx.annotation.LayoutRes
import kotlinx.datetime.LocalDateTime

sealed class ChatUIModel {

    class MyMessageModel(val myMessage: MyMessage): ChatUIModel()

    class NotMyMessageModel(val notMyMessage: NotMyMessage): ChatUIModel()

    class DaySeparationModel(val daySeparation: DaySeparation): ChatUIModel()

}

fun getUserIdByMessageModel(chatUIModel: ChatUIModel): String?{
    return when(chatUIModel){
        is ChatUIModel.MyMessageModel -> { chatUIModel.myMessage.authorId }
        is ChatUIModel.NotMyMessageModel -> { chatUIModel.notMyMessage.authorId }
        is ChatUIModel.DaySeparationModel -> { null }
    }
}

fun getCreationDateByMessageModel(chatUIModel: ChatUIModel): LocalDateTime?{
    return when(chatUIModel){
        is ChatUIModel.MyMessageModel -> { chatUIModel.myMessage.creationDate }
        is ChatUIModel.NotMyMessageModel -> { chatUIModel.notMyMessage.creationDate }
        is ChatUIModel.DaySeparationModel -> { null }
    }
}
