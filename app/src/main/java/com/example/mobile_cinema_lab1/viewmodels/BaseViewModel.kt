package com.example.mobile_cinema_lab1.viewmodels

import android.text.TextUtils
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}