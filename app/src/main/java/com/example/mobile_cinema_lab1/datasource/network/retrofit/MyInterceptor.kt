package com.example.mobile_cinema_lab1.datasource.network.retrofit


import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request.Builder = chain.request().newBuilder().apply {
            addHeader("accept", "application/json")
            //addHeader("content-Type", "application/json")
        }
        val accessToken = com.example.mobile_cinema_lab1.domain.usecases.SharedPreferencesUseCase()
            .getAccessToken()
        if (accessToken != ""){
            request.addHeader("Authorization", "Bearer $accessToken")
        }

        return chain.proceed(request.build())
    }
}