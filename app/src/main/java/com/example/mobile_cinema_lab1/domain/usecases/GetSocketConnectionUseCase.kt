package com.example.mobile_cinema_lab1.domain.usecases

import android.util.Log
import com.example.mobile_cinema_lab1.datasource.network.retrofit.MyAuthenticator
import com.example.mobile_cinema_lab1.datasource.network.retrofit.MyInterceptor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import okhttp3.*
import java.util.concurrent.TimeUnit

class GetSocketConnectionUseCase(private val url: String) {
    operator fun invoke(): Pair<WebSocket, MyWebSocketListener> {
        val client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .authenticator(MyAuthenticator())
            .addInterceptor(MyInterceptor())  // Place token here
            .build()
        val request = Request.Builder()
            .url(url) // Your url
            .build()

        val mySocketListener =
            MyWebSocketListener()

        val socket = client.newWebSocket(request, mySocketListener)

        client.dispatcher.executorService.shutdown()

        return Pair(socket, mySocketListener)
    }
}

class MyWebSocketListener : WebSocketListener(){
    private val flow = MutableSharedFlow<String>()

    fun getFlow() = flow

    override fun onOpen(webSocket: WebSocket, response: Response) {

        Log.d("!", "OPENED!")

        runBlocking {
            flow.emit(response.message)
        }

        super.onOpen(webSocket, response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        runBlocking {
            flow.emit(text)
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("!", "Closing!  $code")
        super.onClosing(webSocket, code, reason)
    }
}