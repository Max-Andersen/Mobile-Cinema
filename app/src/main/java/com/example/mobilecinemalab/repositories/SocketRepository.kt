package com.example.mobilecinemalab.repositories

import com.example.mobilecinemalab.datasource.network.Network
import com.example.mobilecinemalab.datasource.network.retrofit.MyAuthenticator
import com.example.mobilecinemalab.datasource.network.retrofit.MyInterceptor
import com.example.mobilecinemalab.domain.repositoryinterfaces.ISocketRepository
import com.example.mobilecinemalab.domain.usecases.socket.MyWebSocketListener
import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

object SocketRepository : ISocketRepository {

    private lateinit var socket: WebSocket

    private var mySocketListener = MyWebSocketListener()

    override fun initSocket(chatId: String) {
        val url = Network.BASE_URL.replace("http", "ws") + "chats/$chatId/messages"

        val client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .authenticator(MyAuthenticator())
            .addInterceptor(MyInterceptor())  // Place token here
            .build()
        val request = Request.Builder()
            .url(url) // Your url
            .build()

        socket = client.newWebSocket(request, mySocketListener)

        client.dispatcher.executorService.shutdown()
    }

    override fun getMessagesFlow(): MutableSharedFlow<String> {
        return mySocketListener.getFlow()
    }

    override fun sendMessage(message: String) {
        socket.send(message)
    }

    override fun closeSocket() {
        socket.close(1000, "Ok!")
    }
}