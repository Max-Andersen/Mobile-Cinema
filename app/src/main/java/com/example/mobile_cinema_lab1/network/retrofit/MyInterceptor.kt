package com.example.mobile_cinema_lab1.network.retrofit


import com.example.mobile_cinema_lab1.MyApplication
import com.example.mobile_cinema_lab1.network.Network
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request.Builder = chain.request().newBuilder().apply {
            addHeader("accept", "application/json")
            addHeader("content-Type", "application/json")

        }
        val accessToken = Network.getSharedPrefs(MyApplication.AccessToken)
        if (accessToken != ""){
            request.addHeader("Authorization", "Bearer $accessToken")
        }

        return chain.proceed(request.build())
    }
}