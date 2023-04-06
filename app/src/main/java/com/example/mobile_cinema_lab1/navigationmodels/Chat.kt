package com.example.mobile_cinema_lab1.navigationmodels

import android.os.Parcelable
import com.example.mobile_cinema_lab1.network.models.Chat

@kotlinx.parcelize.Parcelize

data class Chat(
    val chatId: String,
    val chatName: String
) : Parcelable


fun Chat.getNavigationModel() = com.example.mobile_cinema_lab1.navigationmodels.Chat(
    chatId = this.chatId,
    chatName = this.chatName
)
