package com.example.mobile_cinema_lab1.datasource.network.retrofit

import com.example.mobile_cinema_lab1.datasource.network.api.AuthApi
import com.example.mobile_cinema_lab1.datasource.network.models.LoginResponse
import com.example.mobile_cinema_lab1.forapplication.errorhandling.TroubleShooting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAuthenticator : Authenticator {

    private val sharedPreferencesUseCase =
        com.example.mobile_cinema_lab1.domain.usecases.SharedPreferencesUseCase()

    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.responseCount >= 5){
            runBlocking {
                withContext(Dispatchers.Main) {
                    if (TroubleShooting.getLiveDataForRefreshTrouble().value != true) {
                        TroubleShooting.updateLiveDataForRefreshTrouble(true)
                    }
                }
            }
            return null
        }

        val refreshToken = sharedPreferencesUseCase.getRefreshToken()
        if (refreshToken != "") {
                val newTokenResponse = runBlocking { getNewToken(refreshToken) }

                if (newTokenResponse.isSuccessful) {
                    newTokenResponse.body()?.let {
                        sharedPreferencesUseCase.updateAccessToken(it.accessToken)
                        sharedPreferencesUseCase.updateRefreshToken(it.refreshToken)

                        return response.request.newBuilder()
                            .header("Authorization", "Bearer ${it.accessToken}")
                            .build()
                    }
                } else {
                    return response.request.newBuilder()
                        .header("Authorization", "Bearer ${sharedPreferencesUseCase.getAccessToken()}")
                        .build()
                }

        }
        return null
    }


    private val Response.responseCount: Int
        get() = generateSequence(this) { it.priorResponse }.count()

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<LoginResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(com.example.mobile_cinema_lab1.datasource.network.Network.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val authApi = retrofit.create(AuthApi::class.java)



        return authApi.refresh("Bearer $refreshToken")
    }

}