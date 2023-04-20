package com.example.mobilecinemalab.domain.usecases

import com.example.mobilecinemalab.datasource.SharedPreferences
import com.example.mobilecinemalab.datasource.SharedPreferencesTypes

class SharedPreferencesUseCase {

    private val types = SharedPreferencesTypes

    fun getAccessToken() = SharedPreferences.getSharedPrefs(types.AccessToken)

    fun updateAccessToken(newToken: String) = SharedPreferences.updateSharedPrefs(types.AccessToken, newToken)

    fun getRefreshToken() = SharedPreferences.getSharedPrefs(types.RefreshToken)

    fun updateRefreshToken(newToken: String) = SharedPreferences.updateSharedPrefs(types.RefreshToken, newToken)

    fun getUserId() = SharedPreferences.getSharedPrefs(types.UserId)

    fun updateUserId(newUserId: String) = SharedPreferences.updateSharedPrefs(types.UserId, newUserId)

    fun getUserName() = SharedPreferences.getSharedPrefs(types.UserName)

    fun updateUserName(newUserName: String) = SharedPreferences.updateSharedPrefs(types.UserName, newUserName)

    fun clearUserData(){
        types.allTypes.forEach {
            SharedPreferences.updateSharedPrefs(it, "")
        }
    }

}