package com.example.mobile_cinema_lab1.network.repositories

import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.network.api.ChatApi
import com.example.mobile_cinema_lab1.usecases.repositoryinterfaces.IChatRepository

class ChatRepository: IChatRepository, BaseRepository() {

    private val chatApi: ChatApi = Network.getUserChats()

    override fun getActiveUserChats() = apiRequestFlow { chatApi.getUserChats() }
}