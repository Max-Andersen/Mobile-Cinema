package com.example.mobile_cinema_lab1.navigationmodels

import android.os.Parcelable

@kotlinx.parcelize.Parcelize

data class Chat(
    val chatId: String,
    val chatName: String
) : Parcelable


fun com.example.mobile_cinema_lab1.datasource.network.models.Chat.getNavigationModel() = Chat(
    chatId = this.chatId,
    chatName = this.chatName
)
