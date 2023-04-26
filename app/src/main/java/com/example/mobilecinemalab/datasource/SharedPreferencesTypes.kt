package com.example.mobilecinemalab.datasource

class SharedPreferencesTypes {
    companion object{
        const val AccessToken: String = "access_token"
        const val RefreshToken: String = "refresh_token"
        const val UserId: String = "user_id"
        const val UserName: String = "user_name"

        const val FirstLaunch: String = "first_launch"

        val allCleanableTypes = listOf(AccessToken, RefreshToken, UserName, UserId)
    }
}