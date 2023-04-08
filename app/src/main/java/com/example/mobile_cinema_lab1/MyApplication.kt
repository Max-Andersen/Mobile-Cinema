package com.example.mobile_cinema_lab1

import android.app.Application
import android.content.Context

class MyApplication: Application() {
    init {
        instance = this
    }

    companion object {

        private var instance: MyApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        const val AccessToken: String = "access_token"
        const val RefreshToken: String = "refresh_token"
        const val UserId: String = "user_id"

    }
}