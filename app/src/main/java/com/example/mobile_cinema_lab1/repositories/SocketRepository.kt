package com.example.mobile_cinema_lab1.repositories

import com.example.mobile_cinema_lab1.datasource.network.Network
import com.example.mobile_cinema_lab1.datasource.network.retrofit.MyAuthenticator
import com.example.mobile_cinema_lab1.datasource.network.retrofit.MyInterceptor
import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.ISocketRepository
import com.example.mobile_cinema_lab1.domain.usecases.MyWebSocketListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

object SocketRepository: ISocketRepository {

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
}