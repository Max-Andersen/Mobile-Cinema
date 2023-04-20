package com.example.mobilecinemalab.ui.compilation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobilecinemalab.datasource.network.ApiResponse
import com.example.mobilecinemalab.datasource.network.models.Movie
import com.example.mobilecinemalab.domain.usecases.movie.GetMoviesUseCase
import com.example.mobilecinemalab.domain.usecases.SetDislikeOnMovie
import com.example.mobilecinemalab.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CompilationViewModel : BaseViewModel() {
    private val compilationLiveData = MutableLiveData<com.example.mobilecinemalab.datasource.network.ApiResponse<List<Movie>>>()

    var compilationMovies = arrayListOf<Movie>()

    private var currentIdx: Int = 0

    fun nextItem() {
        currentIdx += 1
    }

    fun getCurrentItemText(): String {
        return if (currentIdx < compilationMovies.size) compilationMovies[currentIdx].name else ""
    }

    fun getCurrentItem(): Movie? {
        return if (currentIdx < compilationMovies.size) compilationMovies[currentIdx] else null
    }

    fun getCompilationLiveData() = compilationLiveData

    fun getCompilation() {
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            GetMoviesUseCase("compilation")().collect {
                withContext(Dispatchers.Main) {
                    if (it is ApiResponse.Success) {
                        compilationMovies.clear()
                        it.data.forEach { movie ->
                            compilationMovies.add(movie)
                        }
                    }
                    currentIdx = 0
                    compilationLiveData.value = it
                }
            }
        })

    }

    fun onDislikeClick() {
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            getCurrentItem()?.let {
                SetDislikeOnMovie(
                    it.movieId
                )().collect{ resp ->
                    when (resp) {
                        ApiResponse.Loading -> {
                            Log.d("!", "Loading")
                        }
                        is ApiResponse.Success -> {
                            Log.d("!", "Success")
                        }
                        is ApiResponse.Failure -> {
                            Log.d("!", "FAIL")
                        }
                    }
                }
            }
        })

    }
}