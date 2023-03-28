package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.LoginRequestBody
import com.example.mobile_cinema_lab1.network.models.LoginResponse
import com.example.mobile_cinema_lab1.usecases.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignInViewModel: BaseViewModel() {
    private val _data = MutableLiveData<ApiResponse<LoginResponse>>()
    private val _validation = MutableLiveData<String>()

    private var mJob: Job? = null

    fun getLiveDataForRequest() = _data
    fun getLiveDataForValidation() = _validation

    fun login(email: String, password: String){
        var answer = ""
        if (email.isBlank()){
            answer += "Email не может быть пустым\n"
        }

        if (password.isBlank()){
            answer +=  "Пароль не может быть пустым\n"
        }

        if (!email.isEmailValid()){
            answer +=  "Неверный Email\n"
        }

        if (answer.isNotBlank()){
            _validation.value = answer
            return
        }

        viewModelScope.launch(Dispatchers.IO){
            mJob = SignInUseCase(LoginRequestBody(email, password), _data)()
        }
    }

    override fun onCleared() {
        super.onCleared()
        mJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
    }
}