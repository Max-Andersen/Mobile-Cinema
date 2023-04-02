package com.example.mobile_cinema_lab1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.network.ApiResponse
import com.example.mobile_cinema_lab1.network.models.Episode
import com.example.mobile_cinema_lab1.usecases.GetEpisodesOfMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieFrameViewModel : BaseViewModel() {
    private var mJob: Job? = null

    var episodesList = arrayListOf<Episode>()

    private val episodesLiveData = MutableLiveData<ApiResponse<List<Episode>>>()

    fun getLiveDataForEpisodes() = episodesLiveData

    fun getEpisodesOfMovie(movieId: String) {
        mJob = viewModelScope.launch(Dispatchers.IO) {
            GetEpisodesOfMovieUseCase(movieId)().collect { episodes ->
                withContext(Dispatchers.Main) {
                    episodesLiveData.value = episodes
                }
            }
        }

    }

}