package com.example.mobile_cinema_lab1.domain.usecases

import okhttp3.WebSocket

class SendMessageToSocketUseCase( private val socket: WebSocket ) {

    operator fun invoke(messageText: String){
        socket.send(messageText)
    }
}