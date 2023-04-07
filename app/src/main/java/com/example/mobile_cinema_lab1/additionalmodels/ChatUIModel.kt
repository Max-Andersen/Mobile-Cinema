package com.example.mobile_cinema_lab1.additionalmodels

import androidx.annotation.LayoutRes

sealed class ChatUIModel(){

    class MyMessageModel(val myMessage: MyMessage): ChatUIModel()

    class NotMyMessageModel(val notMyMessage: NotMyMessage): ChatUIModel()

    class DaySeparationModel(val daySeparation: DaySeparation): ChatUIModel()

}
