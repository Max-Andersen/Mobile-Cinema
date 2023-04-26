package com.example.mobilecinemalab.repositories

import com.example.mobilecinemalab.datasource.network.Network
import com.example.mobilecinemalab.datasource.network.api.ChatApi
import com.example.mobilecinemalab.domain.repositoryinterfaces.IChatRepository

class ChatRepository: IChatRepository, BaseRepository() {

    private val chatApi: ChatApi = Network.getUserChats()

    override fun getActiveUserChats() = apiRequestFlow { chatApi.getUserChats() }
}