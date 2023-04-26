package com.example.mobilecinemalab.repositories

import com.example.mobilecinemalab.datasource.network.Network
import com.example.mobilecinemalab.datasource.network.api.CoverApi
import com.example.mobilecinemalab.datasource.network.api.HistoryApi
import com.example.mobilecinemalab.datasource.network.api.MovieApi
import com.example.mobilecinemalab.domain.repositoryinterfaces.IMovieRepository

class MovieRepository: IMovieRepository, BaseRepository() {
    private val movieApi: MovieApi = Network.getMovieApi()

    private val coverApi: CoverApi = Network.getCoverApi()

    private val historyApi: HistoryApi = Network.getHistoryApi()

    override fun getCoverOnPromotedFilm() = apiRequestFlow { coverApi.getCover() }

    override fun getMoviesByFilter(filter: String) = apiRequestFlow { movieApi.getMovies(filter) }

    override fun getEpisodeListOfMovie(movieId: String) =
        apiRequestFlow { movieApi.getEpisodesFromMovie(movieId) }

    override fun removeMovieFromCompilation(movieId: String) =
        apiRequestFlow { movieApi.removeFilmFromCompilation(movieId) }

    override fun getUserHistory() = apiRequestFlow { historyApi.getUserHistory() }
}