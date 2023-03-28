package com.example.mobile_cinema_lab1.network.repositories

import com.example.mobile_cinema_lab1.network.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

abstract class BaseRepository {
    protected fun <T> apiRequestFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
        emit(ApiResponse.Loading)

        withTimeoutOrNull(15000L) {
            val response = call()

            try {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        emit(ApiResponse.Success(data))
                    }
                } else {
                    val code = response.code()
                    response.errorBody()?.let { error ->
                        error.close()
//                    val parsedError: ErrorResponse =
//                        Gson().fromJson(error.charStream(), ErrorResponse::class.java)
//                    emit(ApiResponse.Failure(parsedError.message, parsedError.code))
                    }
                    emit(ApiResponse.Failure("Жесть", code))

                }
            } catch (e: Exception) {
                emit(ApiResponse.Failure(e.message ?: e.toString(), 400))
            }
        } ?: emit(ApiResponse.Failure("Timeout! Please try again.", 408))
    }.flowOn(Dispatchers.IO)
}