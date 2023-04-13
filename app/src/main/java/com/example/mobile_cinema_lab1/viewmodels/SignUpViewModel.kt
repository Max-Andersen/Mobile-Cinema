package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.LoginResponse
import com.example.mobile_cinema_lab1.network.models.RegisterRequestBody
import com.example.mobile_cinema_lab1.network.models.UserInfo
import com.example.mobile_cinema_lab1.usecases.UserDataUseCase
import com.example.mobile_cinema_lab1.usecases.SignUpUseCase
import com.example.mobile_cinema_lab1.usecases.ValidateAuthDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel : BaseViewModel() {
    private val _data = MutableLiveData<ApiResponse<LoginResponse>>()
    private val _validation = MutableLiveData<String>()
    private val _userData = MutableLiveData<ApiResponse<UserInfo>>()

    fun getLiveDataForRequest() = _data
    fun getLiveDataForValidation() = _validation

    fun getLiveDataForUserInfo() = _userData

    fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        val validationAnswer = ValidateAuthDataUseCase()(email, password, name, surname, passwordConfirmation)

        if (validationAnswer.isNotBlank()) {
            _validation.value = validationAnswer
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

    fun getUserInfo(){
        mJobs.add(
            viewModelScope.launch(Dispatchers.IO){
                UserDataUseCase().getUserData().collect{
                    withContext(Dispatchers.Main){
                        _userData.value = it
                    }
                }
            }
        )
    }
}