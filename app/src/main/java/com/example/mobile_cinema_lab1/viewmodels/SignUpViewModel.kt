package com.example.mobile_cinema_lab1.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.LoginResponse
import com.example.mobile_cinema_lab1.network.models.RegisterRequestBody
import com.example.mobile_cinema_lab1.usecases.SignUpUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel : BaseViewModel() {
    private val _data = MutableLiveData<ApiResponse<LoginResponse>>()
    private val _validation = MutableLiveData<String>()

    fun getLiveDataForRequest() = _data
    fun getLiveDataForValidation() = _validation

    fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        var answer = ""

        if (name.isBlank()) {
            answer += "Имя не может быть пустым\n"
        }

        if (surname.isBlank()) {
            answer += "Фамилия не может быть пустой\n"
        }

        if (email.isBlank()) {
            answer += "Email не может быть пустым\n"
        }

        if (password.isBlank()) {
            answer += "Пароль не может быть пустым\n"
        }

        if (password != passwordConfirmation) {
            answer += "Пароли не совпадают\n"
        }

        if (!email.isEmailValid()) {
            answer += "Неверный Email\n"
        }

        Log.d("!", answer)

        if (answer.isNotBlank()) {
            _validation.value = answer
            return
        }

        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            SignUpUseCase(RegisterRequestBody(email, password, name, surname))().collect {
                withContext(Dispatchers.Main) {
                    _data.value = it
                }
            }
        })
    }
}