package com.example.mobile_cinema_lab1.repositories

import com.example.mobile_cinema_lab1.datasource.network.Network
import com.example.mobile_cinema_lab1.datasource.network.api.ChatApi
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.IChatRepository

class ChatRepository: IChatRepository, BaseRepository() {

    private val chatApi: ChatApi = Network.getUserChats()

    override fun getActiveUserChats() = apiRequestFlow { chatApi.getUserChats() }
}