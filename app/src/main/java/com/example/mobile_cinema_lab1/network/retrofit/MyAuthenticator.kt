package com.example.mobile_cinema_lab1.network.retrofit


import com.example.mobile_cinema_lab1.MyApplication
import com.example.mobile_cinema_lab1.TroubleShooting
import com.example.mobile_cinema_lab1.network.Network
import com.example.mobile_cinema_lab1.network.api.AuthApi
import com.example.mobile_cinema_lab1.network.models.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        return runBlocking {
            val refreshToken = Network.getSharedPrefs(MyApplication.RefreshToken)
            if (refreshToken != ""){
                val newTokenResponse = getNewToken(refreshToken)

                if (!newTokenResponse.isSuccessful || newTokenResponse.body() == null) {
                    withContext(Dispatchers.Main) {
                        if (TroubleShooting.getLiveDataForRefreshTrouble().value != true){
                            TroubleShooting.updateLiveDataForRefreshTrouble(true)
                        }
                    }
                }

                newTokenResponse.body()?.let {
                    Network.updateSharedPrefs(MyApplication.AccessToken, it.accessToken)
                    Network.updateSharedPrefs(MyApplication.RefreshToken, it.refreshToken)
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${it.accessToken}")
                        .build()
                }
            }
            return@runBlocking response.request
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<LoginResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Network.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val authApi = retrofit.create(AuthApi::class.java)

        return authApi.refresh("Bearer $refreshToken")
    }
}