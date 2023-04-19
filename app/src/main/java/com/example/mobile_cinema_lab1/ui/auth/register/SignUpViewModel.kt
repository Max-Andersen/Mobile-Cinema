package com.example.mobile_cinema_lab1.ui.auth.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.datasource.network.models.LoginResponse
import com.example.mobile_cinema_lab1.datasource.network.models.RegisterRequestBody
import com.example.mobile_cinema_lab1.datasource.network.models.UserInfo
import com.example.mobile_cinema_lab1.domain.usecases.SignUpUseCase
import com.example.mobile_cinema_lab1.domain.usecases.UserDataUseCase
import com.example.mobile_cinema_lab1.domain.usecases.ValidateAuthDataUseCase
import com.example.mobile_cinema_lab1.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel : BaseViewModel() {
    private val _data = MutableLiveData<com.example.mobile_cinema_lab1.datasource.network.ApiResponse<LoginResponse>>()
    private val _validation = MutableLiveData<String>()
    private val _userData = MutableLiveData<com.example.mobile_cinema_lab1.datasource.network.ApiResponse<UserInfo>>()

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