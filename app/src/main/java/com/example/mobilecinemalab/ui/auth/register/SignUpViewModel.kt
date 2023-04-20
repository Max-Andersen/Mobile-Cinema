package com.example.mobilecinemalab.ui.auth.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.Collection
import com.example.mobilecinemalab.datasource.network.models.LoginResponse
import com.example.mobilecinemalab.datasource.network.models.RegisterRequestBody
import com.example.mobilecinemalab.datasource.network.models.UserInfo
import com.example.mobilecinemalab.domain.usecases.SignUpUseCase
import com.example.mobilecinemalab.domain.usecases.UserDataUseCase
import com.example.mobilecinemalab.domain.usecases.ValidateAuthDataUseCase
import com.example.mobilecinemalab.domain.usecases.collection.CreateCollectionUseCase
import com.example.mobilecinemalab.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel : BaseViewModel() {
    private val _data = MutableLiveData<ApiResponse<LoginResponse>>()
    private val _validation = MutableLiveData<String>()
    private val _userData = MutableLiveData<ApiResponse<UserInfo>>()
    private val _createFavourites = MutableLiveData<ApiResponse<Collection>>()

    fun getLiveDataForRequest() = _data
    fun getLiveDataForValidation() = _validation
    fun getLiveDataForUserInfo() = _userData
    fun getLiveDataForFavouriteCollection() = _createFavourites

    fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        val validationAnswer =
            ValidateAuthDataUseCase()(email, password, name, surname, passwordConfirmation)

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

    fun getUserInfo() {
        mJobs.add(
            viewModelScope.launch(Dispatchers.IO) {
                UserDataUseCase().getUserData().collect {
                    withContext(Dispatchers.Main) {
                        _userData.value = it
                    }
                }
            }
        )
    }

    fun createFavouriteCollection() {
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            CreateCollectionUseCase("Избранное")().collect {
                withContext(Dispatchers.Main) {
                    _createFavourites.value = it
                }
            }
        })
    }
}