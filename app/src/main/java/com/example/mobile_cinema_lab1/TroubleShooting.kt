package com.example.mobile_cinema_lab1

import android.util.Log
import androidx.lifecycle.MutableLiveData

interface CoroutinesErrorHandler {
    fun onError(message: String)
}
object TroubleShooting {


    val coroutinesErrorHandler = object : CoroutinesErrorHandler{
        override fun onError(message: String) {
            Log.e("!", "Coroutine Error $message")
        }
    }

    private val failToUpdateToken = MutableLiveData(false)

    fun getLiveDataForRefreshTrouble() = failToUpdateToken

    fun updateLiveDataForRefreshTrouble(newState: Boolean){
        failToUpdateToken.value = newState
    }

}