package com.example.mobilecinemalab.domain.usecases.socket

import com.example.mobilecinemalab.domain.repositoryinterfaces.ISocketRepository
import com.example.mobilecinemalab.repositories.SocketRepository

class CloseSocketUseCase {
    private val socketRepository: ISocketRepository = SocketRepository

    operator fun invoke() {
        socketRepository.closeSocket()
    }
}