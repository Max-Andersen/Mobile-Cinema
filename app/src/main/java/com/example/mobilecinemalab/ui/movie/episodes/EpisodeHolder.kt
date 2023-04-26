package com.example.mobilecinemalab.ui.movie.episodes

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mobilecinemalab.databinding.EpisodeCardBinding
import com.example.mobilecinemalab.datasource.network.models.Episode
import com.example.mobilecinemalab.navigationmodels.getNavigationModel
import com.example.mobilecinemalab.navigationmodels.Movie
import com.example.mobilecinemalab.ui.movie.MovieFragmentDirections

class EpisodeHolder(view: View) : RecyclerView.ViewHolder(view),
    View.OnClickListener {

    private lateinit var data: Episode
    private lateinit var movie: Movie
    private lateinit var yearDuration: () -> String

    private val binding by viewBinding(EpisodeCardBinding::bind)

    fun bind(data: Episode, movie: Movie, yearDuration: () -> String) {
        this.data = data
        this.movie = movie
        this.yearDuration = yearDuration

        Glide.with(itemView.context).load(data.preview).into(binding.episodeImage)
        binding.episodeDescription.text = data.description
        binding.episodeName.text = data.name
        binding.episodeYear.text = data.year.toString()
    }

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        itemView.findNavController().navigate(
            MovieFragmentDirections.actionMovieFragmentToEpisodeFragment(
                data.getNavigationModel(),
                movie,
                yearDuration()
            )
        )
    }
}