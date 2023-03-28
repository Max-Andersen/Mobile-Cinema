package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    fun String.isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches();
    }
}