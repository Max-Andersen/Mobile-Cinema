package com.example.mobile_cinema_lab1.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.mobile_cinema_lab1.MyApplication
import com.example.mobile_cinema_lab1.NavGraphXmlDirections
import com.example.mobile_cinema_lab1.fragments.ErrorDialogFragment
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.network.models.LoginRequestBody
import com.example.mobile_cinema_lab1.network.models.LoginResponse
import com.example.mobile_cinema_lab1.network.models.UserInfo
import com.example.mobile_cinema_lab1.usecases.SignInUseCase
import com.example.mobile_cinema_lab1.usecases.GetUserDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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
        var answer = ""
        if (email.isBlank()) {
            answer += "Email не может быть пустым\n"
        }

        if (password.isBlank()) {
            answer += "Пароль не может быть пустым\n"
        }

        if (!email.isEmailValid()) {
            answer += "Неверный Email\n"
        }

        if (answer.isNotBlank()) {
            _validation.value = answer
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
                GetUserDataUseCase().getUserData().collect{
                    withContext(Dispatchers.Main){
                        _userData.value = it
                    }
                }
            }
        )
    }
}