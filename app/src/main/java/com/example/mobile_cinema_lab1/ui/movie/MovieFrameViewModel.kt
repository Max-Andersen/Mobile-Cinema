package com.example.mobile_cinema_lab1.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobile_cinema_lab1.datasource.network.ApiResponse
import com.example.mobile_cinema_lab1.datasource.network.models.Episode
import com.example.mobile_cinema_lab1.domain.usecases.episode.GetEpisodesOfMovieUseCase
import com.example.mobile_cinema_lab1.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieFrameViewModel : BaseViewModel() {
    var episodesList = arrayListOf<Episode>()

    private val episodesLiveData = MutableLiveData<ApiResponse<List<Episode>>>()

    fun getLiveDataForEpisodes() = episodesLiveData

    fun getEpisodesOfMovie(movieId: String) {
        mJobs.add(viewModelScope.launch(Dispatchers.IO) {
            GetEpisodesOfMovieUseCase(
                movieId
            )().collect { episodes ->
                withContext(Dispatchers.Main) {
                    episodesLiveData.value = episodes
                }
            }
        })
    }

}