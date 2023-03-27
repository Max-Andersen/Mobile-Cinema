package com.example.mobile_cinema_lab1

import androidx.lifecycle.MutableLiveData

object TroubleShooting {

    private val failToUpdateToken = MutableLiveData(false)

    fun getLiveDataForRefreshTrouble() = failToUpdateToken

    fun updateLiveDataForRefreshTrouble(newState: Boolean){
        failToUpdateToken.value = newState
    }

}