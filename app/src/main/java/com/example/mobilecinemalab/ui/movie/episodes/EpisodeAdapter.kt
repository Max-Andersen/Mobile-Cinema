package com.example.mobilecinemalab.ui.movie.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilecinemalab.R
import com.example.mobilecinemalab.datasource.network.models.Episode
import com.example.mobilecinemalab.navigationmodels.Movie

class EpisodeAdapter(
    private var episodes: List<Episode>,
    private var movieData: Movie,
    private var getYearDuration: () -> String
) :
    RecyclerView.Adapter<EpisodeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =
            layoutInflater.inflate(
                R.layout.episode_card,
                parent,
                false
            )

        return EpisodeHolder(view)
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        val episode = episodes[position]
        holder.bind(episode, movieData, getYearDuration)
    }
}