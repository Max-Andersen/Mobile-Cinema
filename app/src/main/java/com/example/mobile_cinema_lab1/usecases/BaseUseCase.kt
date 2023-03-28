package com.example.mobile_cinema_lab1.usecases

import androidx.lifecycle.MutableLiveData
import com.example.mobile_cinema_lab1.CoroutinesErrorHandler
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase {
    suspend fun <T> baseRequest(
        liveData: MutableLiveData<T>,
        errorHandler: CoroutinesErrorHandler,
        request: Flow<T>
    ): Job {
        return CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            CoroutineScope(Dispatchers.Main).launch(Dispatchers.Main) {
                errorHandler.onError(error.localizedMessage ?: "Error occured! Please try again.")
            }
        }).launch {
            request.collect {
                withContext(Dispatchers.Main) {
                    liveData.postValue(it)
                }
            }
        }
    }
}