package com.example.mobile_cinema_lab1.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    protected val mJobs = mutableListOf<Job>()

    override fun onCleared() {
        super.onCleared()
        mJobs.forEach{
            if (it.isActive) {
                it.cancel()
            }
        }
    }

}