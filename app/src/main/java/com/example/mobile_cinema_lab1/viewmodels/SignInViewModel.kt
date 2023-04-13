package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.LoginRequestBody
import com.example.mobile_cinema_lab1.network.models.LoginResponse
import com.example.mobile_cinema_lab1.network.models.UserInfo
import com.example.mobile_cinema_lab1.usecases.SignInUseCase
import com.example.mobile_cinema_lab1.usecases.UserDataUseCase
import com.example.mobile_cinema_lab1.usecases.ValidateAuthDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel : BaseViewModel() {
    private val _data = MutableLiveData<ApiResponse<LoginResponse>>()
    private val _validation = MutableLiveData<String>()

    private val _userData = MutableLiveData<ApiResponse<UserInfo>>()

    fun getLiveDataForRequest() = _data
    fun getLiveDataForValidation() = _validation
    fun getLiveDataForUserInfo() = _userData

    fun login(email: String, password: String) {

        val validationAnswer = ValidateAuthDataUseCase()(email, password)

        if (validationAnswer.isNotBlank()) {
            _validation.value = validationAnswer
            return
        }

        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            SignInUseCase(LoginRequestBody(email, password))().collect {
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