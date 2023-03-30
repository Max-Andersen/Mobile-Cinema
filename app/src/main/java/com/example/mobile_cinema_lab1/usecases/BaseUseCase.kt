package com.example.mobile_cinema_lab1.usecases

import kotlinx.coroutines.flow.Flow

interface BaseUseCase {
    suspend fun <T, U> baseRequest(
        data: U
//        liveData: MutableLiveData<T>,
//        errorHandler: CoroutinesErrorHandler,
//        request: Flow<T>
    ): Flow<T> //{: Job
////        return CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
////            CoroutineScope(Dispatchers.Main).launch(Dispatchers.Main) {
////                errorHandler.onError(error.localizedMessage ?: "Error occured! Please try again.")
////            }
////        }).launch {
//            request.collect {
//                withContext(Dispatchers.Main) {
//                    return@withContext it
//                    //liveData.postValue(it)
//                }
//            }
//        //}
//    }
}