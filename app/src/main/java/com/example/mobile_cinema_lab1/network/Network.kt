package com.example.mobile_cinema_lab1.network

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.mobile_cinema_lab1.MyApplication
import com.example.mobile_cinema_lab1.network.api.*
import com.example.mobile_cinema_lab1.network.retrofit.MyAuthenticator
import com.example.mobile_cinema_lab1.network.retrofit.MyInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {
    const val BASE_URL = "http://107684.web.hosting-russia.ru:8000/api/"

    private var masterKey = MasterKey.Builder(MyApplication.applicationContext())
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences = EncryptedSharedPreferences.create(
        MyApplication.applicationContext(),
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun updateSharedPrefs(typeOfData: String, newToken: String) {
        sharedPreferences.edit().putString(typeOfData, newToken).apply()
    }

    fun getSharedPrefs(typeOfData: String): String? {
        return sharedPreferences.getString(typeOfData, "")
    }

    fun clearUserData() {
        updateSharedPrefs(MyApplication.AccessToken, "")
        updateSharedPrefs(MyApplication.RefreshToken, "")
    }

    private fun getHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(MyInterceptor())
            authenticator(MyAuthenticator())
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
        }
        return client.build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .client(getHttpClient())
            .build()
    }

    private val retrofit: Retrofit = getRetrofit()

    fun getAuthApi(): AuthApi = retrofit.create(AuthApi::class.java)

    fun getCollectionsApi(): CollectionsApi = retrofit.create(CollectionsApi::class.java)

    fun getCoverApi(): CoverApi = retrofit.create(CoverApi::class.java)

    fun getEpisodesApi(): EpisodesApi = retrofit.create(EpisodesApi::class.java)

    fun getHistoryApi(): HistoryApi = retrofit.create(HistoryApi::class.java)

    fun getMovieApi(): MovieApi = retrofit.create(MovieApi::class.java)

    fun getPreferencesApi(): PreferencesApi = retrofit.create(PreferencesApi::class.java)

    fun getProfileApi(): ProfileApi = retrofit.create(ProfileApi::class.java)

    fun getTagsApi(): TagsApi = retrofit.create(TagsApi::class.java)
}

fun <T> apiRequestFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
    emit(ApiResponse.Loading)

    withTimeoutOrNull(15000L) {
        val response = call()

        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(ApiResponse.Success(data))
                }
            } else {
                val code = response.code()
                response.errorBody()?.let { error ->
                    error.close()
//                    val parsedError: ErrorResponse =
//                        Gson().fromJson(error.charStream(), ErrorResponse::class.java)
//                    emit(ApiResponse.Failure(parsedError.message, parsedError.code))
                }
                emit(ApiResponse.Failure("Жесть", code))

            }
        } catch (e: Exception) {
            emit(ApiResponse.Failure(e.message ?: e.toString(), 400))
        }
    } ?: emit(ApiResponse.Failure("Timeout! Please try again.", 408))
}.flowOn(Dispatchers.IO)