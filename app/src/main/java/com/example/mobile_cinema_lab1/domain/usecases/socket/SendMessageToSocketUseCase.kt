package com.example.mobile_cinema_lab1.domain.usecases.socket

import com.example.mobile_cinema_lab1.domain.repositoryinterfaces.ISocketRepository
import com.example.mobile_cinema_lab1.repositories.SocketRepository

class SendMessageToSocketUseCase(private val message: String) {
    private val socketRepository: ISocketRepository = SocketRepository

    operator fun invoke() {
        socketRepository.sendMessage(message)
    }
}