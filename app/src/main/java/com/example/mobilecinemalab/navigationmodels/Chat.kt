package com.example.mobilecinemalab.navigationmodels

import android.os.Parcelable

@kotlinx.parcelize.Parcelize

data class Chat(
    val chatId: String,
    val chatName: String
) : Parcelable


fun com.example.mobilecinemalab.datasource.network.models.Chat.getNavigationModel() = Chat(
    chatId = this.chatId,
    chatName = this.chatName
)
