package com.example.mobile_cinema_lab1.ui.collections.specific

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mobile_cinema_lab1.databinding.CollectionMovieItemBinding
import com.example.mobile_cinema_lab1.datasource.network.models.Movie
import com.example.mobile_cinema_lab1.navigationmodels.getNavigationModel

class CollectionMovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private val binding by viewBinding(CollectionMovieItemBinding::bind)
    private lateinit var movieData: Movie

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(data: Movie) {
        movieData = data
        binding.movieDescription.text = data.description
        Glide.with(itemView).load(data.poster).into(binding.movieImage)
        binding.movieName.text = data.name
    }

    override fun onClick(p0: View?) {
        itemView.findNavController().navigate(
            SpecificCollectionFragmentDirections.actionSpecificCollectionFragmentToMovieFragment(
                movieData.getNavigationModel()
            )
        )
    }
}