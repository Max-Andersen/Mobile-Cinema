package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    protected val mJobs = mutableListOf<Job>()

    fun String.isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches();
    }

    override fun onCleared() {
        super.onCleared()
        mJobs.forEach{
            if (it.isActive) {
                it.cancel()
            }
        }
    }
}