package com.example.mobile_cinema_lab1.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Movie
import com.example.mobile_cinema_lab1.usecases.GetMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CompilationViewModel : ViewModel() {

    private val compilationLiveData = MutableLiveData<ApiResponse<List<Movie>>>()

    var compilationMovies = arrayListOf<Movie>()

    fun getCompilationLiveData() = compilationLiveData

    fun getCompilation() {
        viewModelScope.launch(Dispatchers.IO) {
            GetMoviesUseCase("compilation")().collect {
                withContext(Dispatchers.Main) {
                    if (it is ApiResponse.Success) {
                        compilationMovies.clear()
                        it.data.forEach { movie ->
                            compilationMovies.add(movie)
                        }
                        Log.d("!", "saved")
                    }
                    compilationLiveData.value = it
                }
            }
        }

    }


}